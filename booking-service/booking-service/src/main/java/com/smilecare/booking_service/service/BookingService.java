package com.smilecare.booking_service.service;

import com.smilecare.booking_service.dto.BookingRequestDTO;
import com.smilecare.booking_service.dto.BookingHistoryResponse;
import com.smilecare.booking_service.dto.BookingResponseDTO;
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.entity.BookingServiceAssociation;
import com.smilecare.booking_service.entity.Service;
import com.smilecare.booking_service.repository.BookingRepository;
import com.smilecare.booking_service.repository.BookingServiceRepository;
import com.smilecare.booking_service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate; // <--- QUAN TRỌNG: Thêm cái này để lọc ngày
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

    // Lấy tất cả
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Lấy 1 cái theo ID (Trả về DTO)
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

        // Gán thông tin Bệnh nhân & Bác sĩ
        dto.setPatientInfo(booking.getPatientInfo());
        dto.setScheduleInfo(booking.getScheduleInfo());

        return dto;
    }

    // --- MỚI THÊM: HÀM LẤY LỊCH CHO BÁC SĨ (Dashboard Doctor) ---
    public List<BookingResponseDTO> getBookingsByDoctorAndDate(Integer doctorId, LocalDate date) {
        // Gọi Repository tìm theo Bác sĩ & Ngày
        List<Booking> bookings = bookingRepository.findByDoctorAndDate(doctorId, date);

        // Map sang DTO để Frontend hiển thị được tên Bệnh nhân
        return bookings.stream().map(b -> {
            BookingResponseDTO dto = new BookingResponseDTO();
            dto.setBookingId(b.getId());
            dto.setDateBooking(b.getDateBooking());
            dto.setTimeStart(b.getTimeStart());
            dto.setTimeEnd(b.getTimeEnd());
            dto.setStatus(b.getStatus());
            dto.setDescription(b.getDescription());

            // Quan trọng: Gán User để hiện tên bệnh nhân
            dto.setPatientInfo(b.getPatientInfo());

            // Gán thông tin lịch để hiện tên bác sĩ (nếu cần)
            dto.setScheduleInfo(b.getScheduleInfo());

            return dto;
        }).collect(Collectors.toList());
    }
    // ------------------------------------------------------------

    // Lấy list gốc theo PatientId
    public List<Booking> getBookingsByPatientId(Integer patientId) {
        return bookingRepository.findByPatientIdOrderByDateBookingDesc(patientId);
    }

    // Lấy lịch sử (cho Bệnh nhân xem)
    public List<BookingHistoryResponse> getHistoryByPatientId(Integer patientId) {
        List<Booking> bookings = bookingRepository.findByPatientIdOrderByDateBookingDesc(patientId);

        return bookings.stream().map(booking -> {
            long total = 0;
            List<String> serviceNames = new ArrayList<>();

            if (booking.getBookingServiceAssociations() != null) {
                for (var item : booking.getBookingServiceAssociations()) {
                    if (item.getPriceAtBooking() != null) {
                        total += item.getPriceAtBooking().longValue();
                    }
                    if (item.getService() != null) {
                        serviceNames.add(item.getService().getNameService());
                    }
                }
            }

            String doctorName = "Đang cập nhật";
            if (booking.getScheduleInfo() != null && booking.getScheduleInfo().getDoctorInfo() != null) {
                doctorName = booking.getScheduleInfo().getDoctorInfo().getFullName();
            } else {
                doctorName = "Bác sĩ (Mã " + booking.getScheduleId() + ")";
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

    // Update Status
    public boolean updateStatus(Integer id, String newStatus) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus(newStatus);
            bookingRepository.save(booking);
            return true;
        }).orElse(false);
    }

    // Tạo mới Booking
    @Transactional
    public Booking createBooking(BookingRequestDTO request) {
        Booking newBooking = new Booking();
        newBooking.setDateBooking(request.getDateBooking());
        newBooking.setTimeStart(request.getTimeStart());
        newBooking.setTimeEnd(request.getTimeEnd());
        newBooking.setPatientId(request.getPatientId());
        newBooking.setScheduleId(request.getScheduleId());
        newBooking.setDescription(request.getDescription());
        newBooking.setStatus("PENDING");

        Booking savedBooking = bookingRepository.save(newBooking);

        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            for (Integer serviceId : request.getServiceIds()) {
                Service service = serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy Service ID: " + serviceId));

                BookingServiceAssociation association = new BookingServiceAssociation();
                association.setBooking(savedBooking);
                association.setService(service);
                association.setPriceAtBooking(service.getPrice());

                bookingServiceRepository.save(association);
            }
        }
        return savedBooking;
    }

    public void save(Booking booking) {
        bookingRepository.save(booking);
    }
}