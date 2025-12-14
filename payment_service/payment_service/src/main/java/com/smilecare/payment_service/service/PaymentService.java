package com.smilecare.payment_service.service;

import com.smilecare.payment_service.client.BookingClient;
import com.smilecare.payment_service.dto.ApiResponse;
import com.smilecare.payment_service.dto.BookingDTO;
import com.smilecare.payment_service.dto.PaymentRequestDTO;
import com.smilecare.payment_service.entity.Payment;
import com.smilecare.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingClient bookingClient;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    // --- HÀM NÀY ĐÃ SỬA LỖI ---
    public Payment updatePaymentStatus(Integer id, String status) {
        // SỬA LẠI: Dùng paymentRepository thay vì paymentService
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment createPayment(PaymentRequestDTO request) {
        System.out.println(">>> [SERVICE] Bắt đầu tạo Payment...");

        // 1. Kiểm tra Booking tồn tại
        try {
            ApiResponse<BookingDTO> response = bookingClient.getBookingById(request.getBookingId());
            if (response == null || response.getDT() == null) {
                throw new RuntimeException("Booking ID " + request.getBookingId() + " không tồn tại!");
            }
        } catch (Exception e) {
            System.out.println(">>> [ERROR] Lỗi gọi Booking Service: " + e.getMessage());
            throw new RuntimeException("Lỗi kết nối hoặc không tìm thấy Booking ID");
        }

        // 2. Tạo và Lưu Payment
        Payment newPayment = new Payment();
        newPayment.setBookingId(request.getBookingId());
        newPayment.setAmount(request.getAmount());
        newPayment.setMethod(request.getMethod());
        newPayment.setNote(request.getNote());
        newPayment.setStatus("SUCCESS");
        newPayment.setTransactionCode("TXN-" + UUID.randomUUID());
        newPayment.setCreatedAt(LocalDateTime.now());
        newPayment.setUpdatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(newPayment);
        System.out.println(">>> [SERVICE] Đã lưu Payment ID: " + savedPayment.getId());

        // 3. Gọi Update Booking Status
        try {
            System.out.println(">>> [SERVICE] Đang gọi Booking để update trạng thái...");

            Map<String, String> body = new HashMap<>();
            body.put("status", "CONFIRMED");

            bookingClient.updateBookingStatus(request.getBookingId(), body);

            System.out.println(">>> [SERVICE] Update Booking THÀNH CÔNG!");
        } catch (Exception e) {
            System.err.println(">>> [WARNING] Thanh toán OK nhưng lỗi update Booking: " + e.getMessage());
            e.printStackTrace();
        }

        return savedPayment;
    }
}