
package com.smilecare.booking_service;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingResponseDTO {
    private Integer bookingId;
    private String status;
    private String message;
    private LocalDate dateBooking;
    private LocalTime timeStart;

    // Constructor (Hàm khởi tạo để nạp dữ liệu nhanh)
    public BookingResponseDTO(Integer bookingId, String status, String message, LocalDate dateBooking, LocalTime timeStart) {
        this.bookingId = bookingId;
        this.status = status;
        this.message = message;
        this.dateBooking = dateBooking;
        this.timeStart = timeStart;
    }

    // --- Getters và Setters ---
    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(LocalDate dateBooking) {
        this.dateBooking = dateBooking;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }
}
