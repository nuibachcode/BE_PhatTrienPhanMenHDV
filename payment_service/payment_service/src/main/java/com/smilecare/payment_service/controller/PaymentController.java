package com.smilecare.payment_service.controller;

import com.smilecare.payment_service.dto.PaymentRequestDTO;
import com.smilecare.payment_service.dto.PaymentResponseDTO;
import com.smilecare.payment_service.dto.ApiResponse; // Import ApiResponse
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

    // --- API 1: Lấy tất cả (Trả về ApiResponse<List<DTO>>) ---
    @GetMapping
    public ApiResponse<List<PaymentResponseDTO>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();

        List<PaymentResponseDTO> dtos = payments.stream()
                .map(payment -> new PaymentResponseDTO(payment))
                .collect(Collectors.toList());

        return ApiResponse.success(dtos, "Lấy danh sách thanh toán thành công");
    }

    // --- API 2: Lấy 1 theo ID (Trả về ApiResponse<DTO>) ---
    @GetMapping("/{id}")
    public ApiResponse<?> getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id)
                .map(payment -> ApiResponse.success(new PaymentResponseDTO(payment), "Lấy thông tin thanh toán thành công"))
                .orElse(ApiResponse.error(404, "Không tìm thấy thông tin thanh toán"));
    }

    // --- API 3: Tạo mới (Trả về ApiResponse<DTO>) ---
    @PostMapping
    public ApiResponse<?> createPayment(@RequestBody PaymentRequestDTO request) {
        try {
            Payment newPayment = paymentService.createPayment(request);
            // Gói kết quả vào ApiResponse chuẩn
            return ApiResponse.success(new PaymentResponseDTO(newPayment), "Tạo giao dịch thanh toán thành công");
        } catch (RuntimeException e) {
            // Xử lý lỗi nếu BookingID không tồn tại hoặc lỗi khác
            return ApiResponse.error(400, "Lỗi tạo giao dịch: " + e.getMessage());
        }
    }

    // --- API 4: Cập nhật trạng thái (Trả về ApiResponse<DTO>) ---
    @PutMapping("/{id}/status")
    public ApiResponse<?> updateStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {

        try {
            String status = requestBody.get("status");
            Payment updated = paymentService.updatePaymentStatus(id, status);
            return ApiResponse.success(new PaymentResponseDTO(updated), "Cập nhật trạng thái thành công");
        } catch (RuntimeException e) {
            return ApiResponse.error(404, e.getMessage());
        }
    }
}