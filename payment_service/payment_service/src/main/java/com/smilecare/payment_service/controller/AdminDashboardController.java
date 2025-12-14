package com.smilecare.payment_service.controller;

import com.smilecare.payment_service.client.BookingClient;
import com.smilecare.payment_service.dto.ApiResponse;
import com.smilecare.payment_service.dto.BookingDTO;
import com.smilecare.payment_service.dto.DashboardStatsDTO;
import com.smilecare.payment_service.entity.Payment;
import com.smilecare.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingClient bookingClient; // Dùng cái này để gọi API

    // API 1: Lấy số liệu thống kê (Stats)
    @GetMapping("/stats")
    public ResponseEntity<?> getDashboardStats() {
        try {
            Long today = paymentRepository.getRevenueToday();
            Long month = paymentRepository.getRevenueMonth();
            Long orders = paymentRepository.countOrdersMonth();
            Long doctors = paymentRepository.countDoctors();

            DashboardStatsDTO stats = new DashboardStatsDTO(today, month, orders, doctors);

            return ResponseEntity.ok(Map.of("EC", 0, "DT", stats, "EM", "Lấy thống kê thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("EC", 1, "EM", "Lỗi server: " + e.getMessage()));
        }
    }

    // --- API 2: Lấy giao dịch gần đây (ĐÃ SỬA ĐỂ GỌI SANG BOOKING SERVICE) ---
    @GetMapping("/payments/recent")
    public ResponseEntity<?> getRecentPayments() {
        try {
            // 1. Lấy 10 giao dịch mới nhất từ DB Payment (Chỉ có bookingId, chưa có tên)
            List<Payment> recents = paymentRepository.findTop10ByOrderByCreatedAtDesc();

            // 2. Tạo list Map để trả về Frontend (Custom dữ liệu)
            List<Map<String, Object>> responseList = new ArrayList<>();

            for (Payment payment : recents) {
                // Tạo một Map đại diện cho 1 dòng dữ liệu
                Map<String, Object> item = new HashMap<>();
                item.put("id", payment.getId());
                item.put("amount", payment.getAmount());
                item.put("method", payment.getMethod());
                item.put("status", payment.getStatus());
                item.put("createdAt", payment.getCreatedAt());

                // --- ĐOẠN NÀY QUAN TRỌNG NHẤT: GỌI API ---
                try {
                    // Gọi sang Booking Service bằng Feign Client
                    ApiResponse<BookingDTO> bookingRes = bookingClient.getBookingById(payment.getBookingId());

                    if (bookingRes != null && bookingRes.getDT() != null) {
                        BookingDTO bookingInfo = bookingRes.getDT();

                        // Frontend cần cấu trúc item.Booking.User.fullName
                        // Nên ta phải tạo Map lồng nhau để giả lập cấu trúc đó
                        Map<String, Object> userMap = new HashMap<>();
                        // Lấy patientInfo từ BookingDTO gán vào
//                        if (bookingInfo.getPatientInfo() != null) {
//                            userMap.put("fullName", bookingInfo.getPatientInfo().getFullName());
//                            userMap.put("phone", bookingInfo.getPatientInfo().getPhone());
//                        } else {
//                            userMap.put("fullName", "Không có thông tin");
//                        }
                        // Thay thế bằng cách lấy trực tiếp patientId nếu có trong BookingDTO
                        if (bookingInfo.getPatientId() != null) {
                            userMap.put("patientId", bookingInfo.getPatientId());
                            // Nếu muốn hiển thị tên, bạn phải gọi Feign Client sang User Service TẠI ĐÂY (nhưng ta làm đơn giản trước)
                        } else {
                            userMap.put("patientId", "Không xác định");
                        }


                        Map<String, Object> bookingMap = new HashMap<>();
                        bookingMap.put("User", userMap);

                        // Nhét cục Booking này vào item chính
                        item.put("Booking", bookingMap);
                    }
                } catch (Exception e) {
                    // Nếu Booking Service chết hoặc lỗi mạng, ta fallback để không chết API Dashboard
                    System.err.println("Lỗi gọi Booking Service: " + e.getMessage());
                    Map<String, Object> errorUser = new HashMap<>();
                    errorUser.put("fullName", "Lỗi kết nối Service");
                    item.put("Booking", Map.of("User", errorUser));
                }
                // ------------------------------------------

                responseList.add(item);
            }

            return ResponseEntity.ok(Map.of("EC", 0, "DT", responseList, "EM", "Lấy dữ liệu thành công"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("EC", 1, "EM", "Lỗi server: " + e.getMessage()));
        }
    }
}