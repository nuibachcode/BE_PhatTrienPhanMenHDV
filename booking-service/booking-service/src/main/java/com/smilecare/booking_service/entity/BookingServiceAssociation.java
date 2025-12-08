package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookingservice") // 1. Khớp tên bảng trong XAMPP (viết liền, không gạch dưới)
public class BookingServiceAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 2. Bảng này có ID riêng, không cần dùng khóa gộp (EmbeddedId)

    // --- Quan hệ với bảng Booking ---
    @ManyToOne
    @JoinColumn(name = "bookingId") // 3. Khớp cột 'bookingId' trong ảnh XAMPP
    private Booking booking;

    // --- Quan hệ với bảng Service ---
    @ManyToOne
    @JoinColumn(name = "serviceId") // 4. Khớp cột 'serviceId' trong ảnh XAMPP
    private Service service;

    // --- Giá tại thời điểm đặt ---
    @Column(name = "priceAtBooking") // 5. Khớp cột 'priceAtBooking'
    private Long priceAtBooking;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // --- Constructor rỗng ---
    public BookingServiceAssociation() {
    }

    // --- Tự động lưu thời gian ---
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Long getPriceAtBooking() {
        return priceAtBooking;
    }

    public void setPriceAtBooking(Long priceAtBooking) {
        this.priceAtBooking = priceAtBooking;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}