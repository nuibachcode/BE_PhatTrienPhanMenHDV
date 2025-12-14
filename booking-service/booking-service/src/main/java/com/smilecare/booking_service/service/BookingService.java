package com.smilecare.booking_service.service;

import com.smilecare.booking_service.dto.BookingRequestDTO;
import com.smilecare.booking_service.dto.BookingHistoryResponse;
import com.smilecare.booking_service.dto.BookingResponseDTO;
import com.smilecare.booking_service.dto.PatientRecordResponse;
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.entity.BookingServiceAssociation;
import com.smilecare.booking_service.entity.Service;
import com.smilecare.booking_service.entity.User;
import com.smilecare.booking_service.repository.BookingRepository;
import com.smilecare.booking_service.repository.BookingServiceRepository;
import com.smilecare.booking_service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceRepository bookingServiceRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // --- 1. LẤY TẤT CẢ ---
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // --- 2. LẤY CHI TIẾT ---
    public BookingResponseDTO getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) return null;

        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getId());
        dto.setDateBooking(booking.getDateBooking());
        dto.setTimeStart(booking.getTimeStart());
        dto.setTimeEnd(booking.getTimeEnd());
        dto.setStatus(booking.getStatus());
        dto.setDescription(booking.getDescription());
        dto.setPatientInfo(booking.getPatientInfo());
        dto.setScheduleInfo(booking.getScheduleInfo());

        return dto;
    }

    // --- 3. CẬP NHẬT TRẠNG THÁI ---
    public boolean updateStatus(Integer id, String newStatus) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus(newStatus);
            bookingRepository.save(booking);
            return true;
        }).orElse(false);
    }

    // --- 4. TẠO MỚI BOOKING (ĐÃ UPDATE ĐỂ LƯU LIST DỊCH VỤ) ---
    @Transactional
    public Booking createBooking(BookingRequestDTO request) {
        // B1: Tạo Booking chính
        Booking newBooking = new Booking();
        newBooking.setDateBooking(request.getDateBooking());
        newBooking.setTimeStart(request.getTimeStart());
        newBooking.setTimeEnd(request.getTimeEnd());
        newBooking.setPatientId(request.getPatientId());
        newBooking.setScheduleId(request.getScheduleId());
        newBooking.setDescription(request.getDescription());
        newBooking.setStatus("PENDING");

        Booking savedBooking = bookingRepository.save(newBooking);

        // B2: Lưu danh sách dịch vụ đi kèm (Duyệt qua List<Integer> serviceIds)
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            for (Integer serviceId : request.getServiceIds()) {
                // Tìm thông tin dịch vụ để lấy giá
                Service service = serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy Service ID: " + serviceId));

                // Tạo bản ghi trong bảng trung gian
                BookingServiceAssociation association = new BookingServiceAssociation();
                association.setBooking(savedBooking);
                association.setService(service);
                // Lưu giá tại thời điểm đặt (Snapshot giá)
                association.setPriceAtBooking(service.getPrice());

                bookingServiceRepository.save(association);
            }
        }
        return savedBooking;
    }

    // --- 5. LẤY LỊCH KHÁM CHO BÁC SĨ ---
    public List<BookingResponseDTO> getBookingsByDoctorAndDate(Integer doctorId, LocalDate date) {
        List<Booking> bookings = bookingRepository.findByDoctorAndDate(doctorId, date);
        return bookings.stream().map(b -> {
            BookingResponseDTO dto = new BookingResponseDTO();
            dto.setBookingId(b.getId());
            dto.setDateBooking(b.getDateBooking());
            dto.setTimeStart(b.getTimeStart());
            dto.setTimeEnd(b.getTimeEnd());
            dto.setStatus(b.getStatus());
            dto.setDescription(b.getDescription());
            dto.setPatientInfo(b.getPatientInfo());
            dto.setScheduleInfo(b.getScheduleInfo());
            return dto;
        }).collect(Collectors.toList());
    }

    // --- 6. LẤY LỊCH SỬ KHÁM (Cho Bệnh nhân) ---
    public List<BookingHistoryResponse> getHistoryByPatientId(Integer patientId) {
        List<Booking> bookings = bookingRepository.findByPatientIdOrderByDateBookingDesc(patientId);

        return bookings.stream().map(booking -> {
            long total = 0;
            List<String> serviceNames = new ArrayList<>();

            if (booking.getBookingServiceAssociations() != null) {
                for (var item : booking.getBookingServiceAssociations()) {
                    // Logic tính tiền an toàn (tránh null)
                    Number price = item.getPriceAtBooking();
                    if (price == null && item.getService() != null) {
                        price = item.getService().getPrice();
                    }
                    if (price != null) {
                        total += price.longValue();
                    }

                    if (item.getService() != null) {
                        serviceNames.add(item.getService().getNameService());
                    }
                }
            }

            String doctorName = "Đang cập nhật";
            if (booking.getScheduleInfo() != null && booking.getScheduleInfo().getDoctorInfo() != null) {
                doctorName = booking.getScheduleInfo().getDoctorInfo().getFullName();
            } else if (booking.getScheduleInfo() != null) {
                doctorName = "Bác sĩ (Mã " + booking.getScheduleInfo().getDoctorId() + ")";
            }

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

    // --- 7. LẤY DANH SÁCH BỆNH NHÂN (Hồ sơ bệnh nhân) ---
    public List<PatientRecordResponse> getPatientsForDoctor(Integer doctorId) {
        List<Object[]> results = bookingRepository.findUniquePatientsByDoctor(doctorId);
        List<PatientRecordResponse> responseList = new ArrayList<>();

        for (Object[] row : results) {
            User user = (User) row[0];
            LocalDate lastVisit = (LocalDate) row[1];

            if (user != null) {
                responseList.add(new PatientRecordResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getPhone(),
                        user.getAddress(),
                        lastVisit
                ));
            }
        }
        return responseList;
    }
}