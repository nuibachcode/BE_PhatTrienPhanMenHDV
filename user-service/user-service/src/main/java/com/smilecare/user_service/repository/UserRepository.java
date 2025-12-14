package com.smilecare.user_service.repository;

import com.smilecare.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Kiểm tra trùng email (Thuộc tính email KHÔNG thay đổi)
    boolean existsByEmail(String email);

    // ĐÃ SỬA: Thay thế findByUserName bằng findByAccount
    // Lý do: Thuộc tính được dùng để đăng nhập/xác thực đã được đổi tên từ userName sang account trong Entity User.
    Optional<User> findByAccount(String account);

    // Kiểm tra trùng phone (Thuộc tính phone KHÔNG thay đổi)
    boolean existsByPhone(String phone);
    Optional<User> findByAccountOrEmail(String account, String email);

    List<User> findByRoleId(Integer roleId);
}