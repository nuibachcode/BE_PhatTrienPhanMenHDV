package com.smilecare.booking_service.client;

import com.smilecare.booking_service.dto.ApiResponse;
import com.smilecare.booking_service.dto.ServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "service", url = "http://localhost:8088")
public interface ServiceClient {

    @GetMapping("/api/services")
    ApiResponse<List<ServiceDTO>> getAllServices();

    @GetMapping("/api/services/{id}")
    ApiResponse<ServiceDTO> getServiceById(@PathVariable("id") Integer id);
}