package com.smilecare.payment_service.dto;

public class PaymentRequestDTO {
    private Integer bookingId;
    private Long amount;
    private String method;
    private String note;

    // --- THÊM TRƯỜNG NÀY ---
    private String transactionCode;
    // -----------------------

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // --- THÊM GETTER & SETTER CHO TRANSACTION CODE ---
    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}