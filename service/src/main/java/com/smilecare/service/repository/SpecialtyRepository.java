package com.smilecare.service.repository;

import com.smilecare.service.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    // Hiện tại chưa cần hàm tìm kiếm đặc biệt gì, để trống là đủ dùng CRUD
}