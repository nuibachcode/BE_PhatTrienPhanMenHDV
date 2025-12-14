package com.smilecare.booking_service.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Integer id;

    // Phải đặt đúng tên này thì mới có hàm getFullName()
    private String fullName;

    // Phải có 2 trường này mới có getPhone(), getAddress()
    private String phone;
    private String address;

    private String email; // Thêm cho đủ bộ
}