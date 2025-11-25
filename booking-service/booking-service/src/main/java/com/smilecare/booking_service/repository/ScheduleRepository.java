package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {}