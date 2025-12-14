package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // 1. Tìm lịch sử đặt lịch theo Patient ID (Sắp xếp mới nhất lên đầu)
    List<Booking> findByPatientIdOrderByDateBookingDesc(Integer patientId);

    // 2. Tìm lịch khám cho Bác sĩ (Dashboard Doctor)
    @Query("SELECT b FROM Booking b WHERE b.scheduleInfo.doctorId = :doctorId AND b.dateBooking = :date")
    List<Booking> findByDoctorAndDate(@Param("doctorId") Integer doctorId, @Param("date") LocalDate date);

    // 3. Lấy danh sách bệnh nhân của Bác sĩ (Hồ sơ bệnh nhân)
    @Query("SELECT b.patientInfo, MAX(b.dateBooking) " +
            "FROM Booking b " +
            "JOIN b.patientInfo u " +
            "JOIN b.scheduleInfo s " +
            "WHERE s.doctorId = :doctorId " +
            "GROUP BY b.patientId")
    List<Object[]> findUniquePatientsByDoctor(@Param("doctorId") Integer doctorId);
}