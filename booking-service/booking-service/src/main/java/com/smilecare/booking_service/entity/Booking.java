package com.smilecare.booking_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dateBooking")
    private LocalDate dateBooking;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "timeStart")
    private LocalTime timeStart;

    @Column(name = "timeEnd")
    private LocalTime timeEnd;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // --- 1. GIỮ NGUYÊN ID ĐỂ GHI DỮ LIỆU (WRITE) ---
    @Column(name = "patientId")
    private Integer patientId;

    @Column(name = "scheduleId")
    private Integer scheduleId;

    // --- 2. THÊM MAPPING ĐỂ ĐỌC DỮ LIỆU (READ-ONLY) ---
    // Hibernate sẽ tự động JOIN sang bảng User để lấy tên bệnh nhân
    @ManyToOne
    @JoinColumn(name = "patientId", insertable = false, updatable = false)
    private User patientInfo;

    // Hibernate sẽ tự động JOIN sang bảng Schedule (từ đó lấy Bác sĩ)
    @ManyToOne
    @JoinColumn(name = "scheduleId", insertable = false, updatable = false)
    private Schedule scheduleInfo;

    // ----------------------------------------------------

    @OneToMany(mappedBy = "booking")
//    @JsonIgnore
    private List<BookingServiceAssociation> bookingServiceAssociations;

    public Booking() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "PENDING";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<BookingServiceAssociation> getBookingServiceAssociations() {
        return bookingServiceAssociations;
    }

    public void setBookingServiceAssociations(List<BookingServiceAssociation> bookingServiceAssociations) {
        this.bookingServiceAssociations = bookingServiceAssociations;
    }

    // --- GETTER/SETTER CHO 2 BIẾN MỚI ---
    public User getPatientInfo() { return patientInfo; }
    public void setPatientInfo(User patientInfo) { this.patientInfo = patientInfo; }

    public Schedule getScheduleInfo() { return scheduleInfo; }
    public void setScheduleInfo(Schedule scheduleInfo) { this.scheduleInfo = scheduleInfo; }
}