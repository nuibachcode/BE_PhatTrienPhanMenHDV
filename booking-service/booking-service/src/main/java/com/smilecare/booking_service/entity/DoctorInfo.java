package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "doctorinfo") // Tên bảng trong DB của bạn
public class DoctorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Các trường khớp với ảnh database bạn gửi
    private Integer doctorId;
    private Integer specialtyId;
    private String lever; // "Thạc sĩ, Bác sĩ..."
    private String bio;
    private String avatar;

    // Mapping để lấy tên bác sĩ từ bảng User
    // Frontend dùng d.User.fullName nên biến này phải tên là "user" (viết thường)
    // Jackson sẽ serialize thành "user"
    @OneToOne
    @JoinColumn(name = "doctorId", insertable = false, updatable = false)
    private User user;
}