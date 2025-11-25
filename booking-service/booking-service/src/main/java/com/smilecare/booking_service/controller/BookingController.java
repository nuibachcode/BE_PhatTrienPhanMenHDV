package com.smilecare.booking_service.controller;

import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.dto.BookingRequestDTO;
import com.smilecare.booking_service.dto.BookingResponseDTO;
import com.smilecare.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController // Báo Spring đây là API Controller
@RequestMapping("/api/bookings") // Đặt đường dẫn gốc
public class BookingController {

    @Autowired // Tiêm (Inject) "bộ não" Service vào
    private BookingService bookingService;

    /**
     * API 1: Lấy tất cả
     * (GET http://localhost:8082/api/bookings)
     */
    @GetMapping
    public List<BookingResponseDTO> getAllBookings() {
        // 1. Lấy danh sách Entity từ Service
        List<Booking> bookings = bookingService.getAllBookings();

        // 2. Chuyển đổi từng Entity sang DTO (Dùng Stream)
        return bookings.stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getId(),
                        booking.getStatus(),
                        booking.getDescription(), // Dùng tạm description làm message
                        booking.getDateBooking(),
                        booking.getTimeStart()
                ))
                .collect(Collectors.toList());
    }

    /**
     * API 2: Tạo mới
     * (POST http://localhost:8082/api/bookings)
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO request) {
        try {
            // Gọi logic ở Service
            Booking newBooking = bookingService.createBooking(request);
            // Nếu thành công, trả về 200 OK + booking vừa tạo
//            return ResponseEntity.ok(newBooking);
            // 2. --- PHẦN MỚI: Đóng gói vào Response DTO ---
            BookingResponseDTO response = new BookingResponseDTO(
                    newBooking.getId(),           // Lấy ID vừa tạo
                    newBooking.getStatus(),       // Lấy trạng thái
                    "Đặt lịch thành công!",       // Thêm thông báo
                    newBooking.getDateBooking(),  // Trả lại ngày cho khách kiểm tra
                    newBooking.getTimeStart()     // Trả lại giờ
            );

            // 3. Trả về cái hộp gọn nhẹ này
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Nếu User/Schedule không tìm thấy (lỗi ở Service)
            // Trả về lỗi 400 Bad Request + thông báo lỗi
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}