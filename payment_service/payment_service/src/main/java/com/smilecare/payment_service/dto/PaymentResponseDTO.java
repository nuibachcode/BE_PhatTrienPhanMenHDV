package com.smilecare.payment_service.dto;

import com.smilecare.payment_service.entity.Payment;
import java.time.LocalDateTime;

public class PaymentResponseDTO {
    private Integer id;
    private Long amount;
    private String method;
    private String transactionCode;
    private String status;
    private String note;
    private LocalDateTime createdAt;

    // ⚠️ QUAN TRỌNG: Ở đây KHÔNG khai báo 'booking' hay 'bookingId'
    // -> Kết quả trả về sẽ hoàn toàn ẩn thông tin này.

    // --- Constructor nhận vào Entity (Để Controller dùng) ---
    public PaymentResponseDTO(Payment payment) {
        this.id = payment.getId();
        this.amount = payment.getAmount();
        this.method = payment.getMethod();
        this.transactionCode = payment.getTransactionCode();
        this.status = payment.getStatus();
        this.note = payment.getNote();
        this.createdAt = payment.getCreatedAt();
    }

    // --- Getters và Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}