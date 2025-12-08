package com.smilecare.payment_service.controller;

import com.smilecare.payment_service.dto.PaymentRequestDTO;
import com.smilecare.payment_service.dto.PaymentResponseDTO; // Nhớ import DTO này
import com.smilecare.payment_service.entity.Payment;
import com.smilecare.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // --- API 1: Lấy tất cả (Trả về danh sách DTO) ---
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();

        // Dùng Stream để chuyển đổi từng Entity -> DTO
        List<PaymentResponseDTO> dtos = payments.stream()
                .map(payment -> new PaymentResponseDTO(payment))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // --- API 2: Lấy 1 theo ID (Trả về DTO) ---
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id)
                .map(payment -> ResponseEntity.ok(new PaymentResponseDTO(payment))) // Gói vào DTO
                .orElse(ResponseEntity.notFound().build());
    }

    // --- API 3: Tạo mới (Trả về DTO) ---
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO request) {
        Payment newPayment = paymentService.createPayment(request);
        // Trả về DTO để giấu BookingID ngay khi tạo xong
        return ResponseEntity.ok(new PaymentResponseDTO(newPayment));
    }

    // --- API 4: Cập nhật trạng thái (Trả về DTO) ---
    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDTO> updateStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {

        try {
            String status = requestBody.get("status");
            Payment updated = paymentService.updatePaymentStatus(id, status);
            return ResponseEntity.ok(new PaymentResponseDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}