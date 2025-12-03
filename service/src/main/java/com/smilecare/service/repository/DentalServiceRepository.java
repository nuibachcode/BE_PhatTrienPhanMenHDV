package com.smilecare.service.repository;

import com.smilecare.service.entity.DentalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentalServiceRepository extends JpaRepository<DentalService, Integer> {
    // Đã có sẵn findAll, findById, save, deleteById
}