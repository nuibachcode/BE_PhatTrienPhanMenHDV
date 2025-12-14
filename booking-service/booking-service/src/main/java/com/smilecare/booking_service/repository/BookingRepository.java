package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // 1. Tìm lịch sử đặt lịch theo Patient ID (Sắp xếp mới nhất lên đầu)
    // Trường patientId là Integer -> OK
    List<Booking> findByPatientIdOrderByDateBookingDesc(Integer patientId);

    // 2. Tìm lịch khám cho Bác sĩ (Dashboard Doctor)
    // Tối ưu: Tìm trực tiếp theo doctorId trong bảng Booking (vì ta đã thêm cột này vào Entity rồi)
    // Không cần join sang scheduleInfo nữa cho nặng
    List<Booking> findByDoctorIdAndDateBooking(Integer doctorId, LocalDate dateBooking);

    // 3. Lấy danh sách bệnh nhân của Bác sĩ (Hồ sơ bệnh nhân)
    // SỬA: Chỉ lấy patientId, KHÔNG JOIN bảng User
    @Query("SELECT b.patientId, MAX(b.dateBooking) " +
            "FROM Booking b " +
            "WHERE b.doctorId = :doctorId " +
            "GROUP BY b.patientId")
    List<Object[]> findUniquePatientsByDoctor(@Param("doctorId") Integer doctorId);
}