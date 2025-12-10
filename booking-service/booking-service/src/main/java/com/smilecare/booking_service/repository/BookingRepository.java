package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <--- Thêm import này
import org.springframework.data.repository.query.Param; // <--- Thêm import này
import java.time.LocalDate; // <--- Thêm import này
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // 1. Tìm theo Patient ID (Code cũ của bạn)
    List<Booking> findByPatientIdOrderByDateBookingDesc(Integer patientId);

    // 2. --- THÊM HÀM NÀY ĐỂ BÁC SĨ XEM LỊCH ---
    // Logic: Tìm trong bảng Booking, join sang ScheduleInfo để check DoctorId
    @Query("SELECT b FROM Booking b WHERE b.scheduleInfo.doctorId = :doctorId AND b.dateBooking = :date")
    List<Booking> findByDoctorAndDate(@Param("doctorId") Integer doctorId, @Param("date") LocalDate date);
}