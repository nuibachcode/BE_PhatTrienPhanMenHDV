package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Tìm kiếm User theo Role ID (Ví dụ: 2 là Bác sĩ)
    List<User> findByRoleId(Integer roleId);
}