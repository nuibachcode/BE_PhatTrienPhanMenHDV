package com.smilecare.booking_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookingservice") // 1. Khớp tên bảng trong XAMPP
public class BookingServiceAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // --- QUAN TRỌNG: THÊM @JsonIgnore ĐỂ TRÁNH LỖI VÒNG LẶP ---
    @ManyToOne
    @JoinColumn(name = "bookingId")
    @JsonIgnore // <--- BẮT BUỘC PHẢI CÓ
    private Booking booking;
    // -----------------------------------------------------------

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;

    @Column(name = "priceAtBooking")
    private Long priceAtBooking;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public BookingServiceAssociation() {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // --- Getters & Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }

    public Long getPriceAtBooking() { return priceAtBooking; }
    public void setPriceAtBooking(Long priceAtBooking) { this.priceAtBooking = priceAtBooking; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}