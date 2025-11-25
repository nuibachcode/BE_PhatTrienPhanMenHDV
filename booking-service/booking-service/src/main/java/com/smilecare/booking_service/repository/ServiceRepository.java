package com.smilecare.booking_service.repository;


import com.smilecare.booking_service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ServiceRepository extends JpaRepository<Service, Integer> {}