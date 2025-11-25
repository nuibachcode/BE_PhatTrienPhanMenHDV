package com.smilecare.payment_service.entity;
import jakarta.persistence.*;

@Entity // 1. Báo cho JPA biết đây là 1 bảng
// 2. Ánh xạ đến bảng tên 'payment'
@Table(name = "payment")
public class Payment {

    @Id // 3. Đánh dấu đây là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. ID này tự động tăng
    private Integer id; // 5. Khớp với `id (int)`

    private Long amount; // 6. Khớp với `amount (bigint)`


    private String method; // Với biến 'method'
    @Column(name = "transaction_code")
    private String transactionCode;
    private String note;
    private String status;
//EAGER: verbose
    // --- Định nghĩa quan hệ ---
    @ManyToOne(fetch = FetchType.EAGER) // 8. Nhiều Payment thuộc về 1 Booking

    @JoinColumn(name = "booking_id") // 9. Thông qua cột khóa ngoại 'BookingId'
    private Booking booking;

    // --- Bắt buộc: Constructor rỗng ---
    public Payment() {
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
// --- Getters và Setters ---
    // (Bạn tự dùng IDE để sinh (generate)
    // getter/setter cho TẤT CẢ các trường trên)
    // ...
}