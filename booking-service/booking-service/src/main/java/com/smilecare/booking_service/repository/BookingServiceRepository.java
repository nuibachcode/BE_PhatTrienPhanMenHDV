package com.smilecare.booking_service.repository;

import com.smilecare.booking_service.entity.BookingServiceAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingServiceRepository extends JpaRepository<BookingServiceAssociation, Integer> {
    // File này dùng để lưu dữ liệu vào bảng bookingservice
}