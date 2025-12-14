package com.smilecare.booking_service.dto.response;

import lombok.Data;
import java.time.LocalTime;
import java.util.List;

@Data
public class DoctorBookingResponse {
    private Integer id; // ID Booking
    private String status;
    private String description;

    // Thời gian
    private LocalTime timeStart;
    private LocalTime timeEnd;

    // Thông tin Bệnh nhân (Frontend gọi là item.User)
    private PatientInfoDTO User;

    // Dịch vụ (Frontend gọi là item.services)
    private List<ServiceDTO> services;

    @Data
    public static class PatientInfoDTO {
        private Integer id;
        private String fullName;
        private String phone;
        private String address;
    }

    @Data
    public static class ServiceDTO {
        private String nameService;
    }
}