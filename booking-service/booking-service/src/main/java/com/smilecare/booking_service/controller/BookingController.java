package com.smilecare.booking_service.controller;

import com.smilecare.booking_service.dto.BookingRequestDTO;
import com.smilecare.booking_service.dto.BookingResponseDTO; // Import DTO của bạn
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Lấy tất cả (Giữ nguyên trả về Entity hoặc đổi sang DTO nếu muốn)
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Lấy 1 cái theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        return bookingService.getBookingById(id)
                .map(booking -> {
                    // Chuyển Entity -> ResponseDTO
                    BookingResponseDTO response = new BookingResponseDTO(
                            booking.getId(),
                            booking.getStatus(),
                            "Tìm thấy lịch hẹn thành công",
                            booking.getDateBooking(),
                            booking.getTimeStart()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- API TẠO MỚI (QUAN TRỌNG NHẤT) ---
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        // 1. Gọi Service tạo booking
        Booking newBooking = bookingService.createBooking(request);

        // 2. Đóng gói vào DTO của bạn để trả về
        BookingResponseDTO response = new BookingResponseDTO(
                newBooking.getId(),             // Map ID
                newBooking.getStatus(),         // Map Status
                "Đặt lịch thành công! Vui lòng chờ xác nhận.", // Message thông báo
                newBooking.getDateBooking(),    // Map Ngày
                newBooking.getTimeStart()       // Map Giờ
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getHistory(@PathVariable Integer patientId) {
        List<Booking> list = bookingService.getBookingsByPatientId(patientId);
        // Trả về danh sách trực tiếp (Frontend sẽ nhận được mảng JSON)
        return ResponseEntity.ok(list);
    }
}