package com.smilecare.payment_service.service;

import com.smilecare.payment_service.dto.PaymentRequestDTO;
import com.smilecare.payment_service.entity.Booking;
import com.smilecare.payment_service.entity.Payment;
import com.smilecare.payment_service.repository.BookingRepository;
import com.smilecare.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // 1. Đánh dấu đây là lớp Service (chứa logic)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Autowired // 2. Tự động "tiêm" Repository vào đây
    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    // --- Logic 1: Lấy tất cả ---
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // --- Logic 2: Lấy 1 theo ID ---
    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id); // Dùng Optional để tránh lỗi null
    }

    // --- Logic 3: Tạo mới (Quan trọng nhất) ---
    public Payment createPayment(PaymentRequestDTO request) {
        // A. Kiểm tra xem bookingId có thật không
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Booking ID: " + request.getBookingId()));

        // B. Tạo đối tượng Payment mới
        Payment newPayment = new Payment();
        newPayment.setAmount(request.getAmount());
        newPayment.setMethod(request.getMethod()); // Sẽ map vào cột 'method'
        newPayment.setNote(request.getNote());
        newPayment.setStatus("PENDING"); // Mặc định là PENDING

        // C. Gán liên kết
        newPayment.setBooking(booking);

        // D. Lưu vào DB và trả về
        return paymentRepository.save(newPayment);
    }

    // --- Logic 4: Cập nhật trạng thái ---
    public Payment updatePaymentStatus(Integer id, String status) {
        // A. Tìm payment
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Payment ID: " + id));

        // B. Cập nhật trạng thái
        payment.setStatus(status);

        // C. Lưu lại
        return paymentRepository.save(payment);
    }
}