package com.smilecare.payment_service.controller;

import com.smilecare.payment_service.dto.PaymentRequestDTO;
import com.smilecare.payment_service.dto.PaymentResponseDTO;
import com.smilecare.payment_service.dto.ApiResponse;
import com.smilecare.payment_service.entity.Payment;
import com.smilecare.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // --- API 3: Tạo mới (CÓ LOG DEBUG) ---
    @PostMapping
    public ApiResponse<?> createPayment(@RequestBody PaymentRequestDTO request) {
        // LOG 1: Bắt đầu nhận request
        System.out.println(">>> [DEBUG 1] Controller nhận request: BookingId=" + request.getBookingId());

        try {
            // LOG 2: Chuẩn bị gọi Service
            System.out.println(">>> [DEBUG 2] Đang gọi paymentService.createPayment()...");

            Payment newPayment = paymentService.createPayment(request);

            // LOG 3: Service chạy xong, trả về kết quả
            System.out.println(">>> [DEBUG 3] Service trả về thành công. PaymentID=" + newPayment.getId());

            return ApiResponse.success(new PaymentResponseDTO(newPayment), "Tạo giao dịch thanh toán thành công");

        } catch (Exception e) {
            // LOG ERROR: Nếu lỗi thì in chi tiết ra Console ngay
            System.out.println(">>> [DEBUG ERROR] Lỗi xảy ra tại Controller!");
            e.printStackTrace(); // <-- QUAN TRỌNG: In stack trace để xem lỗi dòng nào

            return ApiResponse.error(400, "Lỗi tạo giao dịch: " + e.getMessage());
        }
    }

    // --- (Các API khác giữ nguyên hoặc tạm thời chưa quan tâm) ---

    @GetMapping
    public ApiResponse<List<PaymentResponseDTO>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        List<PaymentResponseDTO> dtos = payments.stream()
                .map(payment -> new PaymentResponseDTO(payment))
                .collect(Collectors.toList());
        return ApiResponse.success(dtos, "Lấy danh sách thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id)
                .map(payment -> ApiResponse.success(new PaymentResponseDTO(payment), "Thành công"))
                .orElse(ApiResponse.error(404, "Không tìm thấy"));
    }

    @PutMapping("/{id}/status")
    public ApiResponse<?> updateStatus(@PathVariable Integer id, @RequestBody Map<String, String> requestBody) {
        try {
            String status = requestBody.get("status");
            Payment updated = paymentService.updatePaymentStatus(id, status);
            return ApiResponse.success(new PaymentResponseDTO(updated), "Cập nhật thành công");
        } catch (RuntimeException e) {
            return ApiResponse.error(404, e.getMessage());
        }
    }
}