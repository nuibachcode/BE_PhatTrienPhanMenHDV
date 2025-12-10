package com.smilecare.payment_service.entity;

import jakarta.persistence.*;
import lombok.Data; // Dùng Lombok cho gọn
import java.time.LocalDateTime;

@Entity
@Data // Tự sinh Getter/Setter
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "bookingId")
    private Integer bookingId;



    private Long amount;

    private String method;

    @Column(name = "transactionCode")
    private String transactionCode;

    private String note;

    private String status;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Payment() {
    }

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