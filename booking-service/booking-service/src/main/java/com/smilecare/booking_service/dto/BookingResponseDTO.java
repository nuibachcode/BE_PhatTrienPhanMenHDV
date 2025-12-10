package com.smilecare.booking_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingResponseDTO {
    private Integer bookingId;
    private String status;
    private String message;
    private LocalDate dateBooking;
    private LocalTime timeStart;
    private LocalTime timeEnd; // Thêm timeEnd cho đầy đủ
    private String description;

    // --- ⚠️ QUAN TRỌNG: 2 trường này để Payment Service lấy tên ---
    // Dùng kiểu Object để hứng bất kỳ dữ liệu nào (Entity User, Doctor...)
    private Object patientInfo;
    private Object scheduleInfo;
    // -----------------------------------------------------------

    // 1. Constructor rỗng (Bắt buộc phải có để Service tạo đối tượng)
    public BookingResponseDTO() {
    }

    // 2. Constructor đầy đủ (Nếu bạn muốn dùng)
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

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // --- Getter/Setter cho PatientInfo và ScheduleInfo (QUAN TRỌNG) ---
    public Object getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(Object patientInfo) {
        this.patientInfo = patientInfo;
    }

    public Object getScheduleInfo() {
        return scheduleInfo;
    }

    public void setScheduleInfo(Object scheduleInfo) {
        this.scheduleInfo = scheduleInfo;
    }
}