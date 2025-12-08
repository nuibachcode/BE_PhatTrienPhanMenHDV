package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "booking") // Khớp tên bảng trong XAMPP
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Khớp cột 'dateBooking'
    @Column(name = "dateBooking")
    private LocalDate dateBooking;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    // Khớp cột 'timeStart'
    @Column(name = "timeStart")
    private LocalTime timeStart;

    // Khớp cột 'timeEnd'
    @Column(name = "timeEnd")
    private LocalTime timeEnd;

    // Khớp cột 'patientId'
    @Column(name = "patientId")
    private Integer patientId;

    // Khớp cột 'scheduleId'
    @Column(name = "scheduleId")
    private Integer scheduleId;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public Booking() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        // Mặc định status nếu null
        if (status == null) status = "PENDING";
    }

    // --- Getters & Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDate getDateBooking() { return dateBooking; }
    public void setDateBooking(LocalDate dateBooking) { this.dateBooking = dateBooking; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalTime getTimeStart() { return timeStart; }
    public void setTimeStart(LocalTime timeStart) { this.timeStart = timeStart; }
    public LocalTime getTimeEnd() { return timeEnd; }
    public void setTimeEnd(LocalTime timeEnd) { this.timeEnd = timeEnd; }
    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }
    public Integer getScheduleId() { return scheduleId; }
    public void setScheduleId(Integer scheduleId) { this.scheduleId = scheduleId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}