package com.smilecare.user_service.repository;

import com.smilecare.user_service.entity.DoctorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Integer> {
    // Thêm phương thức tìm kiếm theo ID của Doctor (User ID) nếu cần
    // Optional<DoctorInfo> findByDoctor_Id(Long doctorId);
    Optional<DoctorInfo> findByDoctor_Id(Integer doctorId);
}