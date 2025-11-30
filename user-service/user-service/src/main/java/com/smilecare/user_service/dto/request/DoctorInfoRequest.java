package com.smilecare.user_service.dto.request;

import lombok.Data;

@Data
public class DoctorInfoRequest {

    // --- THÔNG TIN BẢNG DOCTORINFO ---

    // ID của bác sĩ (User ID)
    private Integer doctorId;

    // Khóa ngoại đến bảng Specialty
    private Integer specialtyId;

    // Học vị/Chức danh (qualification -> lever)
    private String lever;

    // Giới thiệu bản thân (description -> bio)
    private String bio;

    // *Tùy chọn: Thêm trường avatar nếu cần*
    // private String avatar;

    // --- THÔNG TIN BẢNG USER (CÁ NHÂN) ---

    private String fullName;
    private String phone;
    private String address;
}