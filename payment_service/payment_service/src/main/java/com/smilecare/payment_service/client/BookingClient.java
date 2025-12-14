package com.smilecare.payment_service.client;

import com.smilecare.payment_service.dto.BookingDTO;
import com.smilecare.payment_service.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

// --- QUAN TRỌNG: THÊM url="http://localhost:8082" VÀO ---
// Vì chưa có Eureka Server, ta phải chỉ đích danh địa chỉ nhà của Booking Service
@FeignClient(name = "booking-service", url = "http://localhost:8082")
public interface BookingClient {

    @GetMapping("/api/bookings/{id}")
    ApiResponse<BookingDTO> getBookingById(@PathVariable("id") Integer id);

    @PutMapping("/api/bookings/{id}")
    ApiResponse<String> updateBookingStatus(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> body
    );
}