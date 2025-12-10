package com.smilecare.payment_service.dto;
import lombok.Data;

@Data
public class BookingDTO {
    private Integer id;
    private UserDTO patientInfo; // JSON trả về có cục patientInfo
    // private UserDTO doctorInfo; // Nếu cần tên bác sĩ thì thêm vào
}