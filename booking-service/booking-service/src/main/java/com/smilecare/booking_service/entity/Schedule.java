package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule") // Khớp tên bảng trong XAMPP
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Khớp cột 'dateWork'
    @Column(name = "dateWork")
    private LocalDate dateWork;

    // Khớp cột 'timeStart'
    @Column(name = "timeStart")
    private LocalTime timeStart;

    // Khớp cột 'timeEnd'
    @Column(name = "timeEnd")
    private LocalTime timeEnd;

    // Khớp cột 'maxPatient'
    @Column(name = "maxPatient")
    private Integer maxPatient;

    @Column(name = "description")
    private String description;

    // Khớp cột 'doctorId'
    @Column(name = "doctorId")
    private Integer doctorId;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Schedule() {
    }

    // --- Tự động điền ngày giờ (QUAN TRỌNG) ---
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
}