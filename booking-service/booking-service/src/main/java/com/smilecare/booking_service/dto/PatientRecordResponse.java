package com.smilecare.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRecordResponse {
    private Integer id;          // ID Bệnh nhân
    private String fullName;     // Họ tên đầy đủ
    private String phoneNumber;  // Số điện thoại
    private String address;      // Địa chỉ
    private LocalDate lastVisit; // Ngày khám gần nhất
}