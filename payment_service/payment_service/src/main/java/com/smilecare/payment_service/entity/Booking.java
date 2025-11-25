package com.smilecare.payment_service.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Booking {

    @Id
    private Integer id; // Phải là Integer để khớp với `BookingId (int)`

    // Getters và Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}