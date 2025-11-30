package com.smilecare.user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Integer id; // <-- Đổi Long thành Integer
    private String fullName;
    private String account;
    private String email;
    private String phone;
    private String address;
    private String roleName;
    private Integer roleId; // <-- Đổi Long thành Integer
}