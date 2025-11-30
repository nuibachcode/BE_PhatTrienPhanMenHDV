package com.smilecare.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "doctorInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bio;

    @Column(name = "lever", nullable = false)
    private String lever;

    // Khóa ngoại 1-1 liên kết với User (Bác sĩ). Dùng ID của User làm khóa chính cũng được,
    // nhưng dùng khóa chính riêng là an toàn nhất.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId", nullable = false, unique = true)
    private User doctor;

    // Khóa ngoại Many-to-One liên kết với Specialty (EAGER để tự động tải)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialtyId", nullable = false)
    private Specialty specialty;
}