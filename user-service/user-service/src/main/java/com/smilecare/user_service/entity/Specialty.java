package com.smilecare.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "specialty")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nameSpecialty", nullable = false, unique = true)
    private String nameSpecialty;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
}