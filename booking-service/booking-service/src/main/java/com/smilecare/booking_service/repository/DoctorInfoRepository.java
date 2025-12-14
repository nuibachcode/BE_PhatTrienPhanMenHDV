package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.DoctorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Integer> {
}