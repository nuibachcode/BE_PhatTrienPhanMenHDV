package com.smilecare.booking_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class BookingRequestDTO {

    private Integer userId;
    private Integer scheduleId;
    private Set<Integer> serviceIds; // Danh sách ID các dịch vụ

    // Các thông tin khác
    private LocalDate dateBooking;
    private LocalTime timeStart;
    private String description;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Set<Integer> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(Set<Integer> serviceIds) {
        this.serviceIds = serviceIds;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}