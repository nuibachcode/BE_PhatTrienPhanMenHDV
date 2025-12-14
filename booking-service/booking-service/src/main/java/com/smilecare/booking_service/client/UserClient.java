package com.smilecare.booking_service.client;

import com.smilecare.booking_service.dto.ApiResponse;
import com.smilecare.booking_service.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8087")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    ApiResponse<DoctorDTO> getUserById(@PathVariable("id") Integer id);

    @GetMapping("/api/users/doctors")
    ApiResponse<List<DoctorDTO>> getAllDoctors();
}