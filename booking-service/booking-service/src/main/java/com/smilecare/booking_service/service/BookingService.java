package com.smilecare.booking_service.service;

// 1. CHỈ IMPORT @Service CỦA SPRING
import com.smilecare.booking_service.dto.BookingRequestDTO;
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.entity.Schedule;
import com.smilecare.booking_service.entity.User;
import com.smilecare.booking_service.repository.BookingRepository;
import com.smilecare.booking_service.repository.ScheduleRepository;
import com.smilecare.booking_service.repository.ServiceRepository;
import com.smilecare.booking_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
// (Không import Service của bạn ở đây)

@Service // 2. Giữ nguyên @Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * Logic 1: Lấy tất cả các booking
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Logic 2: Tạo một booking mới (ĐÃ SỬA LỖI ĐỤNG HÀNG)
     */
    public Booking createBooking(BookingRequestDTO request) {

        // A. Tìm User (Giữ nguyên)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User: " + request.getUserId()));

        // B. Tìm Schedule (Giữ nguyên)
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Schedule: " + request.getScheduleId()));

        // --- SỬA 1 (Dòng 43 cũ) ---
        // Dùng tên đầy đủ: "com.smilecare.booking_service.entity.Service"
        List<com.smilecare.booking_service.entity.Service> servicesToBook =
                serviceRepository.findAllById(request.getServiceIds());

        // D. Tạo đối tượng Booking mới (Giữ nguyên)
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setStatus("PENDING");
        booking.setDateBooking(request.getDateBooking());
        booking.setTimeStart(request.getTimeStart());
        booking.setDescription(request.getDescription());

        // E. LƯU LẦN 1 (BẮT BUỘC)
        Booking savedBooking = bookingRepository.save(booking);

        // F. Tạo và thêm các liên kết
        // --- SỬA 2 (Dòng 61 cũ) ---
        // Dùng tên đầy đủ: "com.smilecare.booking_service.entity.Service"
        for (com.smilecare.booking_service.entity.Service s : servicesToBook) {

            // --- SỬA 3 (Dòng 64 cũ) ---
            // 's' bây giờ đã là Entity, nên .getPrice() sẽ hoạt động
            int currentPrice = s.getPrice().intValue();

            // Dòng 67 cũ bây giờ cũng sẽ hoạt động
            savedBooking.addService(s, currentPrice);
        }

        // G. LƯU LẦN 2
        return bookingRepository.save(savedBooking);
    }
}