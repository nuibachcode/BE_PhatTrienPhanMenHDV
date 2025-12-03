package com.smilecare.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "specialty") // Tên bảng trong DB
@Data
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nameSpecialty") // Map với cột nameSpecialty trong ảnh
    private String nameSpecialty;

    @Column(name = "description")
    private String description;

    @CreationTimestamp // Tự động lấy giờ hiện tại khi tạo
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Tự động cập nhật giờ khi sửa
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}