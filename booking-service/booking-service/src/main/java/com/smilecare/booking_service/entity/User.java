package com.smilecare.booking_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user") // 1. Khớp tên bảng 'user' trong XAMPP
public class User {

    @Id
    private Integer id;

    // --- Constructor rỗng (Bắt buộc) ---
    public User() {
    }

    // --- Constructor có tham số (Tiện khi cần gán ID nhanh) ---
    public User(Integer id) {
        this.id = id;
    }

    // --- Getters & Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}