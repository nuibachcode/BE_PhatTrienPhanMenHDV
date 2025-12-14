package com.smilecare.booking_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user") // Map vào bảng 'user' trong database chung
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address") // Thêm trường này để hiển thị trong Hồ sơ bệnh nhân
    private String address;

    @Column(name = "email")   // Thêm email nếu cần hiển thị
    private String email;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}