package com.smilecare.booking_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;// Khuyên dùng Lombok cho gọn
import java.time.LocalDateTime;

@Entity
@Table(name = "bookingservice")
@Data
public class BookingServiceAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    @JsonIgnore
    @ToString.Exclude
    private Booking booking;

    @Column(name = "serviceId")
    private Integer serviceId;

    // ------------------------------------------

    @Column(name = "priceAtBooking")
    private Long priceAtBooking;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // --- PrePersist / PreUpdate ---
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}