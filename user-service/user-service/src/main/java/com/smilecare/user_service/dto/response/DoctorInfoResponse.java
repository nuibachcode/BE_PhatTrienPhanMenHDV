package com.smilecare.user_service.dto.response;

import lombok.Data;

@Data
public class DoctorInfoResponse {
    // Thông tin từ DoctorInfo
    private Integer id;
    private String avatar;
    private String bio;
    private String lever;

    // Thông tin từ User (Bác sĩ)
    private UserDTO doctor;

    // Thông tin từ Specialty
    private SpecialtyDTO specialty;

    // DTO lồng nhau cho User (để loại bỏ password, role...)
    @Data
    public static class UserDTO {
        private Integer id;
        private String fullName;
        private String email;
        private String phone;
        private String address;
    }

    // DTO lồng nhau cho Specialty
    @Data
    public static class SpecialtyDTO {
        private Integer id;
        private String nameSpecialty;
        private String description;
    }
}