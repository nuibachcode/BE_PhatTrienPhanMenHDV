package com.smilecare.payment_service.dto;

import com.smilecare.payment_service.entity.Payment;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data // Tự sinh Getter, Setter, toString... cho gọn
@NoArgsConstructor
public class PaymentResponseDTO {
    private Integer id;
    private Long amount;
    private String method;
    private String transactionCode;
    private String status;
    private String note;
    private LocalDateTime createdAt;

    // ✅ QUAN TRỌNG: Giữ lại ID này để Frontend biết tiền này của Booking nào
    private Integer bookingId;

    // --- Constructor ---
    public PaymentResponseDTO(Payment payment) {
        this.id = payment.getId();
        this.amount = payment.getAmount();
        this.method = payment.getMethod();
        this.transactionCode = payment.getTransactionCode();
        this.status = payment.getStatus();
        this.note = payment.getNote();
        this.createdAt = payment.getCreatedAt();

        // Map ID từ Entity sang DTO
        this.bookingId = payment.getBookingId();
    }
}