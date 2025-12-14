package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    // Tìm lịch theo ID bác sĩ, sắp xếp ngày tăng dần
    List<Schedule> findByDoctorIdOrderByDateWorkAsc(Integer doctorId);
}