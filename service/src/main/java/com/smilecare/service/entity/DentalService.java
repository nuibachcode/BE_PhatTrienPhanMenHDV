package com.smilecare.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "service")
@Data
public class DentalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // --- SỬA ĐOẠN NÀY ---
    @Column(name = "nameService") // 1. Map đúng tên cột trong DB
    @JsonProperty("nameService")  // 2. Tên key trong JSON trả về
    private String nameService;   // 3. Đổi tên biến từ 'name' sang 'nameService'

    @Column(name = "price")
    private Long price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "specialtyId")
    private Integer specialtyId;

    // --- Thêm đoạn này để hiện thông tin Chuyên khoa lồng bên trong ---
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialtyId", insertable = false, updatable = false)
    @JsonIgnoreProperties({"createdAt", "updatedAt", "description"})
    @JsonProperty("Specialty") // Trả về key là "Specialty" (viết hoa S)
    private Specialty specialty;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}