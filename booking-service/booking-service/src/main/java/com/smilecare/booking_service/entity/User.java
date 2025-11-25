package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "user")
public class User {
    @Id
    private Integer id;
    // Getters/Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}