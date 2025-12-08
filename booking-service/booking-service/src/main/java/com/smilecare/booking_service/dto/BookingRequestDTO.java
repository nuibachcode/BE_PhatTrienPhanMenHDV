package com.smilecare.booking_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List; // Nhớ import cái này

public class BookingRequestDTO {
    // --- Các trường cũ ---
    private LocalDate dateBooking;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Integer patientId;
    private Integer scheduleId;
    private String description;

    // --- MỚI THÊM: Danh sách dịch vụ khách chọn ---
    // (Ví dụ: [1, 2] -> Khách chọn dịch vụ số 1 và số 2)
    private List<Integer> serviceIds;

    // --- Getters & Setters ---

    // Getter/Setter cho serviceIds (Cái bạn đang thiếu)
    public List<Integer> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Integer> serviceIds) {
        this.serviceIds = serviceIds;
    }

    // ... Các Getter/Setter cũ giữ nguyên
    public LocalDate getDateBooking() { return dateBooking; }
    public void setDateBooking(LocalDate dateBooking) { this.dateBooking = dateBooking; }

    public LocalTime getTimeStart() { return timeStart; }
    public void setTimeStart(LocalTime timeStart) { this.timeStart = timeStart; }

    public LocalTime getTimeEnd() { return timeEnd; }
    public void setTimeEnd(LocalTime timeEnd) { this.timeEnd = timeEnd; }

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    public Integer getScheduleId() { return scheduleId; }
    public void setScheduleId(Integer scheduleId) { this.scheduleId = scheduleId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}