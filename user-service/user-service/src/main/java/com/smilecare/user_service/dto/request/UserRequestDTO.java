package com.smilecare.user_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    // Tên field từ Frontend
    private String fullName; // Map vào Name
    private String account;  // Map vào UserName
    private String email;
    private String phone;
    private String password;
    private String address;

    // Role ID mặc định khi đăng ký (sẽ set cứng là 2 trong Service)
    private Integer roleId;
}