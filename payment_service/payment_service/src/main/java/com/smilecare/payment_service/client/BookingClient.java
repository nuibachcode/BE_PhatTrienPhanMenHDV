package com.smilecare.payment_service.client;

import com.smilecare.payment_service.dto.BookingDTO;
import com.smilecare.payment_service.dto.ApiResponse; // Dùng lại class ApiResponse bạn đã có
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name: tên service, url: đường dẫn tới Gateway (8080) hoặc trực tiếp service kia
@FeignClient(name = "booking-service", url = "http://localhost:8080/api/bookings")
public interface BookingClient {

    // Gọi API: GET http://localhost:8080/api/bookings/{id}
    // Giả sử API bên Booking trả về dạng chuẩn ApiResponse
    @GetMapping("/{id}")
    ApiResponse<BookingDTO> getBookingById(@PathVariable("id") Integer id);
}