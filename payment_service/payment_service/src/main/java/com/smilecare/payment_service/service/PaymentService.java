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

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(PaymentRequestDTO request) {
        // Kiểm tra Booking có tồn tại không
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Booking ID: " + request.getBookingId()));

        Payment newPayment = new Payment();
        newPayment.setAmount(request.getAmount());
        newPayment.setMethod(request.getMethod());
        newPayment.setNote(request.getNote());

        // Tự động sinh mã giao dịch (TXN + Thời gian)
        newPayment.setTransactionCode("TXN-" + System.currentTimeMillis());

        newPayment.setStatus("PENDING");
        newPayment.setBooking(booking);

        return paymentRepository.save(newPayment);
    }

    public Payment updatePaymentStatus(Integer id, String status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Payment ID: " + id));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}