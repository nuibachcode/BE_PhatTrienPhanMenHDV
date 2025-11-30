package com.smilecare.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "role") // QUAN TRỌNG: Tên bảng là 'role' (chữ thường)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Trong ảnh 1, tên cột là NameRole
    @Column(name = "NameRole")
    private String nameRole;

    @Column(name = "Description")
    private String description;

    // Phần này không liên quan tới DB, chỉ để Java xử lý 2 chiều
    @OneToMany(mappedBy = "role")
    private List<User> users;
}