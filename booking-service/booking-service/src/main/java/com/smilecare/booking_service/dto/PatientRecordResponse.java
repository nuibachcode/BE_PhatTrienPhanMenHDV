package com.smilecare.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor // <--- Bắt buộc có để fix lỗi hàm new()
@NoArgsConstructor
public class PatientRecordResponse {
    private Integer patientId;
    private String patientName; // Tương ứng với fullName
    private String phone;
    private String address;
    private LocalDate lastVisitDate;
}