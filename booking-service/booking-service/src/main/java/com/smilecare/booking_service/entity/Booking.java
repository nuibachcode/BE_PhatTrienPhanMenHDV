package com.smilecare.booking_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "booking")
@Data
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

    // --- FOREIGN KEYS (LƯU ID) ---

    @Column(name = "patientId")
    private Integer patientId;

    @Column(name = "doctorId")
    private Integer doctorId;

    @Column(name = "scheduleId")
    private Integer scheduleId;

    // --- RELATIONSHIPS (QUAN HỆ NỘI BỘ) ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Schedule scheduleInfo;

    @OneToMany(mappedBy = "booking")
    @ToString.Exclude
    private List<BookingServiceAssociation> bookingServiceAssociations;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = "PENDING";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}