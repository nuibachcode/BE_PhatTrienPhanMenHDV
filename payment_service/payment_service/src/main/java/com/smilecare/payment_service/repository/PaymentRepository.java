package com.smilecare.payment_service.repository;

import com.smilecare.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    // 1. Lấy 10 giao dịch gần nhất (Cho bảng Recent Payments)
    // Sắp xếp ngày tạo giảm dần
    List<Payment> findTop10ByOrderByCreatedAtDesc();

    // 2. Tính Tổng Doanh thu Hôm nay
    @Query(value = "SELECT COALESCE(SUM(amount), 0) FROM payment WHERE DATE(createdAt) = CURDATE() AND status = 'SUCCESS'", nativeQuery = true)
    Long getRevenueToday();

    // 3. Tính Tổng Doanh thu Tháng này
    @Query(value = "SELECT COALESCE(SUM(amount), 0) FROM payment WHERE MONTH(createdAt) = MONTH(CURRENT_DATE()) AND YEAR(createdAt) = YEAR(CURRENT_DATE()) AND status = 'SUCCESS'", nativeQuery = true)
    Long getRevenueMonth();

    // 4. Đếm Tổng số đơn (Booking) tháng này (Query sang bảng booking)
    @Query(value = "SELECT COUNT(*) FROM booking WHERE MONTH(dateBooking) = MONTH(CURRENT_DATE()) AND YEAR(dateBooking) = YEAR(CURRENT_DATE())", nativeQuery = true)
    Long countOrdersMonth();

    // 5. Đếm Tổng số Bác sĩ (User có roleId = 2) (Query sang bảng user)
    @Query(value = "SELECT COUNT(*) FROM user WHERE roleId = 2", nativeQuery = true)
    Long countDoctors();
}