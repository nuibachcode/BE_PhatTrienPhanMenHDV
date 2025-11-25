package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Integer> {}
