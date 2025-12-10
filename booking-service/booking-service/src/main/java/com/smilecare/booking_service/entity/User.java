package com.smilecare.booking_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user") // Chuẩn tên bảng số ít
public class User {

    @Id
    private Integer id;

    // --- BẮT BUỘC PHẢI CÓ CÁC CỘT NÀY ĐỂ HIỂN THỊ TÊN ---
    @Column(name = "fullName")
    private String fullName;

    @Column(name = "phone")
    private String phone;
    // ----------------------------------------------------
    @Column(name = "roleId")
    private Integer roleId;
    public User() {
    }

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

    // Phải có Getter thì JSON mới xuất ra được
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
}