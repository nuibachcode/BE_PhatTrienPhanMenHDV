package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dateWork")
    private LocalDate dateWork;

    @Column(name = "timeStart")
    private LocalTime timeStart;

    @Column(name = "timeEnd")
    private LocalTime timeEnd;

    @Column(name = "maxPatient")
    private Integer maxPatient;

    @Column(name = "description")
    private String description;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // --- 1. GIỮ NGUYÊN CỘT ID (Để lưu dữ liệu) ---
    @Column(name = "doctorId")
    private Integer doctorId;

    // --- 2. THÊM MAPPING (Để lấy thông tin Bác sĩ) ---
    // Join với bảng User thông qua cột doctorId
    // insertable = false, updatable = false: Để tránh xung đột với cột Integer ở trên
    @ManyToOne
    @JoinColumn(name = "doctorId", insertable = false, updatable = false)
    private User doctorInfo;

    public Schedule() {
    }

    // --- Tự động điền ngày giờ ---
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // --- GETTER & SETTER ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDateWork() { return dateWork; }
    public void setDateWork(LocalDate dateWork) { this.dateWork = dateWork; }

    public LocalTime getTimeStart() { return timeStart; }
    public void setTimeStart(LocalTime timeStart) { this.timeStart = timeStart; }

    public LocalTime getTimeEnd() { return timeEnd; }
    public void setTimeEnd(LocalTime timeEnd) { this.timeEnd = timeEnd; }

    public Integer getMaxPatient() { return maxPatient; }
    public void setMaxPatient(Integer maxPatient) { this.maxPatient = maxPatient; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // --- Getter & Setter cho Object User (Bác sĩ) ---
    public User getDoctorInfo() { return doctorInfo; }
    public void setDoctorInfo(User doctorInfo) { this.doctorInfo = doctorInfo; }
}