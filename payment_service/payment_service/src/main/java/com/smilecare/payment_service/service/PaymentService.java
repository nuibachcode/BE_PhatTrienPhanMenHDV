package com.smilecare.payment_service.service;

import com.smilecare.payment_service.client.BookingClient;
import com.smilecare.payment_service.dto.ApiResponse;
import com.smilecare.payment_service.dto.BookingDTO;
import com.smilecare.payment_service.dto.PaymentRequestDTO;
import com.smilecare.payment_service.entity.Payment;
import com.smilecare.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // --- THAY THẾ: Dùng Client thay vì Repository ---
    private final BookingClient bookingClient;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, BookingClient bookingClient) {
        this.paymentRepository = paymentRepository;
        this.bookingClient = bookingClient;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(PaymentRequestDTO request) {
        // --- 1. SỬA ĐOẠN CHECK TỒN TẠI (Dùng API) ---
        // Gọi sang Booking Service để kiểm tra xem ID có thật không
        try {
            ApiResponse<BookingDTO> response = bookingClient.getBookingById(request.getBookingId());
            // Kiểm tra nếu API trả về null hoặc Data (DT) bị null -> Nghĩa là không có booking này
            if (response == null || response.getDT() == null) {
                throw new RuntimeException("Booking ID không tồn tại hoặc Service lỗi");
            }
        } catch (Exception e) {
            // Nếu Service bên kia sập hoặc trả về 404
            throw new RuntimeException("Không tìm thấy Booking ID: " + request.getBookingId());
        }
        // --------------------------------------------

        Payment newPayment = new Payment();

        // 2. Set các thông tin cơ bản
        newPayment.setAmount(request.getAmount());
        newPayment.setMethod(request.getMethod());
        newPayment.setNote(request.getNote());

        // Sinh mã giao dịch
        if (request.getTransactionCode() != null && !request.getTransactionCode().isEmpty()) {
            newPayment.setTransactionCode(request.getTransactionCode());
        } else {
            newPayment.setTransactionCode("TXN-" + System.currentTimeMillis());
        }

        // --- GIỮ NGUYÊN STATUS SUCCESS ---
        newPayment.setStatus("SUCCESS");
        // ---------------------------------

        // 3. Set Booking ID
        newPayment.setBookingId(request.getBookingId());

        return paymentRepository.save(newPayment);
    }

    public Payment updatePaymentStatus(Integer id, String status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Payment ID: " + id));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}