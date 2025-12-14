package com.smilecare.payment_service.client;

import com.smilecare.payment_service.dto.BookingDTO;
import com.smilecare.payment_service.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// SỬA: CHỈ DÙNG 'name' VÀ XÓA 'url'
// Tên service phải khớp với tên Booking Service đăng ký trong Eureka (Ví dụ: booking-service)
@FeignClient(name = "booking-service")
public interface BookingClient {

    // LƯU Ý: Endpoint phải là đường dẫn nội bộ của Booking Service (ví dụ: /api/bookings/{id})
    // Không cần /api/bookings ở đây nữa vì nó đã được chỉ định ở @FeignClient
    @GetMapping("/api/bookings/{id}") // Giữ nguyên đường dẫn đầy đủ của Booking Service
    ApiResponse<BookingDTO> getBookingById(@PathVariable("id") Integer id);
}