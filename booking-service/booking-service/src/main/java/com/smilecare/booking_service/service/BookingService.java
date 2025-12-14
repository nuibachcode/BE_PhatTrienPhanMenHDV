package com.smilecare.booking_service.service;

import com.smilecare.booking_service.client.ServiceClient;
import com.smilecare.booking_service.client.UserClient;
import com.smilecare.booking_service.dto.*;
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.entity.BookingServiceAssociation;
import com.smilecare.booking_service.repository.BookingRepository;
import com.smilecare.booking_service.repository.BookingServiceRepository;
import com.smilecare.booking_service.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingServiceRepository bookingServiceRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ServiceClient serviceClient;
    @Autowired
    private UserClient userClient;


    // --- CÁC HÀM GET CƠ BẢN ---
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public BookingResponseDTO getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) return null;
        return mapToBookingResponse(booking);
    }

    @Transactional
    public boolean updateStatus(Integer id, String newStatus) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus(newStatus);
            bookingRepository.save(booking);
            return true;
        }).orElse(false);
    }

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO request) {
        // 1. Map dữ liệu và Lưu Booking
        Booking newBooking = new Booking();
        newBooking.setDateBooking(request.getDateBooking());
        newBooking.setTimeStart(request.getTimeStart());
        newBooking.setTimeEnd(request.getTimeEnd());
        newBooking.setPatientId(request.getPatientId());
        newBooking.setDoctorId(request.getDoctorId());
        newBooking.setScheduleId(request.getScheduleId());
        newBooking.setDescription(request.getDescription());
        newBooking.setStatus("PENDING");

        Booking savedBooking = bookingRepository.save(newBooking);

        // 2. Lưu các dịch vụ (BookingServiceAssociation)
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            for (Integer serviceId : request.getServiceIds()) {
                Double price = 0.0;
                try {
                    ApiResponse<ServiceDTO> response = serviceClient.getServiceById(serviceId);
                    if (response.getDT() != null) {
                        price = response.getDT().getPrice().doubleValue();
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi Service Client: " + e.getMessage());
                }

                BookingServiceAssociation association = new BookingServiceAssociation();
                association.setBooking(savedBooking);
                association.setServiceId(serviceId);
                association.setPriceAtBooking(price.longValue());
                bookingServiceRepository.save(association);
            }
        }

        // --- BƯỚC 3: TẠO RESPONSE VÀ BỒI THÊM SCHEDULE INFO ---

        // Tạo response cơ bản (lúc này scheduleInfo đang null)
        BookingResponseDTO finalResponse = mapToBookingResponse(savedBooking);

        // Tự tay lấy Schedule từ DB và nhét vào Response
        if (request.getScheduleId() != null) {
            scheduleRepository.findById(request.getScheduleId())
                    .ifPresent(schedule -> {
                        finalResponse.setScheduleInfo(schedule);
                    });
        }

        return finalResponse;
    }

    // --- LỊCH SỬ KHÁM (SỬA Constructor và .getDT()) ---
    public List<BookingHistoryResponse> getHistoryByPatientId(Integer patientId) {
        List<Booking> bookings = bookingRepository.findByPatientIdOrderByDateBookingDesc(patientId);

        return bookings.stream().map(booking -> {
            long total = 0;
            List<String> serviceNames = new ArrayList<>();

            if (booking.getBookingServiceAssociations() != null) {
                for (BookingServiceAssociation item : booking.getBookingServiceAssociations()) {
                    if (item.getPriceAtBooking() != null) total += item.getPriceAtBooking();
                    try {
                        ApiResponse<ServiceDTO> sRes = serviceClient.getServiceById(item.getServiceId());
                        // SỬA: .getDT()
                        if (sRes.getDT() != null) {
                            serviceNames.add(sRes.getDT().getNameService());
                        }
                    } catch (Exception e) {
                        serviceNames.add("Dịch vụ " + item.getServiceId());
                    }
                }
            }

            String doctorName = "Đang tải...";
            try {
                ApiResponse<DoctorDTO> uRes = userClient.getUserById(booking.getDoctorId());
                // SỬA: .getDT()
                if (uRes.getDT() != null) {
                    doctorName = uRes.getDT().getFullName();
                }
            } catch (Exception e) {
                doctorName = "Bác sĩ #" + booking.getDoctorId();
            }

            // SỬA: Constructor phải khớp thứ tự file DTO bạn gửi
            // id, dateBooking, timeStart, timeEnd, status, description, doctorName, totalAmount, serviceNames
            return new BookingHistoryResponse(
                    booking.getId(),
                    booking.getDateBooking(),
                    booking.getTimeStart(),
                    booking.getTimeEnd(),
                    booking.getStatus(),
                    booking.getDescription(),
                    doctorName,
                    total,
                    serviceNames
            );
        }).collect(Collectors.toList());
    }

    // --- CÁC HÀM KHÁC (Cũng sửa .getDT()) ---
    public List<BookingResponseDTO> getBookingsByDoctorAndDate(Integer doctorId, LocalDate date) {
        List<Booking> bookings = bookingRepository.findByDoctorIdAndDateBooking(doctorId, date);
        return bookings.stream().map(this::mapToBookingResponse).collect(Collectors.toList());
    }

    // Hàm này cho BookingController gọi để lấy danh sách
    public List<ServiceDTO> getAllServicesFromRemote() {
        // SỬA: .getDT()
        return serviceClient.getAllServices().getDT();
    }

    public List<DoctorDTO> getAllDoctorsFromRemote() {
        // SỬA: .getDT()
        return userClient.getAllDoctors().getDT();
    }

    // Hàm lấy bệnh nhân cho bác sĩ (Logic cũ)
    public List<PatientRecordResponse> getPatientsForDoctor(Integer doctorId) {
        List<Object[]> results = bookingRepository.findUniquePatientsByDoctor(doctorId);
        List<PatientRecordResponse> responseList = new ArrayList<>();
        for (Object[] row : results) {
            Integer patientId = (Integer) row[0];
            LocalDate lastVisit = (LocalDate) row[1];
            try {
                ApiResponse<DoctorDTO> userRes = userClient.getUserById(patientId);
                // SỬA: .getDT()
                if (userRes.getDT() != null) {
                    DoctorDTO u = userRes.getDT();
                    // Lưu ý: Constructor PatientRecordResponse phải có @AllArgsConstructor
                    responseList.add(new PatientRecordResponse(
                            patientId, u.getFullName(), u.getPhone(), u.getAddress(), lastVisit
                    ));
                }
            } catch (Exception e) {}
        }
        return responseList;
    }

    // --- 6. HÀM MAPPER (SỬA LẠI ĐOẠN NÀY ĐỂ LẤY FULL INFO) ---
    private BookingResponseDTO mapToBookingResponse(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();

        // 1. Map thông tin cơ bản
        dto.setBookingId(booking.getId());
        dto.setDateBooking(booking.getDateBooking());
        dto.setStatus(booking.getStatus());
        dto.setDescription(booking.getDescription());
        dto.setTimeStart(booking.getTimeStart());
        dto.setTimeEnd(booking.getTimeEnd());

        // 2. Map thông tin SCHEDULE (Lấy từ quan hệ Entity JPA)
        // Vì trong Entity Booking bạn đã map @ManyToOne Schedule scheduleInfo
        if (booking.getScheduleInfo() != null) {
            dto.setScheduleInfo(booking.getScheduleInfo());
        }

        // 3. Map thông tin BỆNH NHÂN (Gọi sang User Service)
        if (booking.getPatientId() != null) {
            try {
                // Gọi Feign Client sang User Service (Port 8087)
                ApiResponse<DoctorDTO> userRes = userClient.getUserById(booking.getPatientId());
                // Check kỹ: Khác null và getDT khác null
                if (userRes != null && userRes.getDT() != null) {
                    dto.setPatientInfo(userRes.getDT());
                }
            } catch (Exception e) {
                // Nếu lỗi kết nối thì set thông báo lỗi nhẹ
                dto.setPatientInfo("Lỗi tải thông tin bệnh nhân ID: " + booking.getPatientId());
            }
        }

        return dto;
    }
}