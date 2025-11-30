package com.smilecare.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; // Thêm import cho createdAt và updatedAt

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Ánh xạ tới cột 'fullName' trong DB (tương ứng với fullName trong Sequelize)
    @Column(name = "fullName", nullable = false)
    private String fullName;

    // Ánh xạ tới cột 'account' trong DB (tương ứng với account trong Sequelize)
    @Column(name = "account", nullable = false, unique = true)
    private String account;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    // Ánh xạ tới cột 'passWord' trong DB (tương ứng với passWord trong Sequelize)
    @Column(name = "passWord", nullable = false)
    private String passWord;

    @Column(name = "address")
    private String address;

    // Khóa ngoại: roleId (Tương ứng với roleId trong Sequelize)
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    // Thêm createdAt và updatedAt nếu bạn muốn quản lý chúng trong Spring Boot
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}