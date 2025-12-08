package com.smilecare.booking_service.service;

import com.smilecare.booking_service.dto.BookingRequestDTO;
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.entity.BookingServiceAssociation;
import com.smilecare.booking_service.entity.Service;
import com.smilecare.booking_service.repository.BookingRepository;
import com.smilecare.booking_service.repository.BookingServiceRepository;
import com.smilecare.booking_service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service; // Cẩn thận import nhầm class Service của mình
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service // Dùng tường minh để tránh nhầm với Entity Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceRepository bookingServiceRepository; // Repo mới để lưu bảng trung gian

    @Autowired
    private ServiceRepository serviceRepository; // Repo để lấy giá dịch vụ

    // Lấy tất cả
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Lấy 1 cái theo ID
    public Optional<Booking> getBookingById(Integer id) {
        return bookingRepository.findById(id);
    }
    public List<Booking> getBookingsByPatientId(Integer patientId) {
        return bookingRepository.findByPatientIdOrderByDateBookingDesc(patientId);
    }
    // --- TẠO MỚI BOOKING (KÈM DỊCH VỤ) ---
    @Transactional // Quan trọng: Để đảm bảo lưu cả 2 bảng cùng lúc, lỗi thì rollback
    public Booking createBooking(BookingRequestDTO request) {

        // 1. Tạo và Lưu thông tin Booking trước
        Booking newBooking = new Booking();
        newBooking.setDateBooking(request.getDateBooking());
        newBooking.setTimeStart(request.getTimeStart());
        newBooking.setTimeEnd(request.getTimeEnd());
        newBooking.setPatientId(request.getPatientId());
        newBooking.setScheduleId(request.getScheduleId());
        newBooking.setDescription(request.getDescription());

        // Set trạng thái mặc định
        newBooking.setStatus("PENDING");

        // Lưu xuống DB để lấy ID
        Booking savedBooking = bookingRepository.save(newBooking);

        // 2. Xử lý lưu danh sách Dịch vụ (Nếu có)
        // Check xem khách có gửi kèm serviceIds không (Ví dụ: [1, 2])
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {

            for (Integer serviceId : request.getServiceIds()) {
                // A. Tìm thông tin dịch vụ gốc để lấy giá tiền
                Service service = serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy Service có ID: " + serviceId));

                // B. Tạo bản ghi trong bảng trung gian (BookingServiceAssociation)
                BookingServiceAssociation association = new BookingServiceAssociation();
                association.setBooking(savedBooking); // Gắn với Booking vừa tạo
                association.setService(service);      // Gắn với Service tìm được

                // Lưu giá tiền tại thời điểm đặt (Quan trọng cho doanh thu sau này)
                association.setPriceAtBooking(service.getPrice());

                // C. Lưu vào bảng bookingservice
                bookingServiceRepository.save(association);
            }
        }

        return savedBooking;
    }
}