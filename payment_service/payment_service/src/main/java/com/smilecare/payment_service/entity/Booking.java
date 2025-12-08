package com.smilecare.payment_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // Nhớ import cái này

@Entity
@Table(name = "booking") // 1. BẮT BUỘC: Chỉ định rõ tên bảng trong XAMPP
public class Booking {

    @Id
    private Integer id;

    // --- Constructor rỗng (Bắt buộc theo chuẩn JPA) ---
    public Booking() {
    }

    // --- Constructor có tham số (Tiện khi cần new Booking(1)) ---
    public Booking(Integer id) {
        this.id = id;
    }

    // --- Getters và Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}