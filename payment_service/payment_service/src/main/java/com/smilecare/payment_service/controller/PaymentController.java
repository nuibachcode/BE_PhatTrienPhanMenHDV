package com.smilecare.payment_service.controller;



import com.smilecare.payment_service.entity.Payment;
import com.smilecare.payment_service.dto.PaymentRequestDTO;
import com.smilecare.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController // 1. Báo Spring đây là API Controller (trả về JSON)
@RequestMapping("/api/payments") // 2. Đường dẫn gốc cho tất cả API
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // --- API 1: Lấy tất cả ---
    // (GET http://localhost:8083/api/payments)
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // --- API 2: Lấy 1 theo ID ---
    // (GET http://localhost:8083/api/payments/1)
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) { // 3. Lấy id từ URL
        return paymentService.getPaymentById(id)
                .map(payment -> ResponseEntity.ok(payment)) // 4. Nếu tìm thấy, trả về 200 OK
                .orElse(ResponseEntity.notFound().build()); // 5. Nếu không, trả về 404 Not Found
    }

    // --- API 3: Tạo mới ---
    // (POST http://localhost:8083/api/payments)
    @PostMapping
    public Payment createPayment(@RequestBody PaymentRequestDTO request) { // 6. Lấy data từ JSON
        return paymentService.createPayment(request);
    }

    // --- API 4: Cập nhật trạng thái ---
    // (PUT http://localhost:8083/api/payments/1/status)
    @PutMapping("/{id}/status")
    public ResponseEntity<Payment> updateStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) { // 7. Nhận JSON đơn giản

        try {
            String status = requestBody.get("status"); // Lấy giá trị từ {"status": "COMPLETED"}
            Payment updated = paymentService.updatePaymentStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy ID
        }
    }
}
