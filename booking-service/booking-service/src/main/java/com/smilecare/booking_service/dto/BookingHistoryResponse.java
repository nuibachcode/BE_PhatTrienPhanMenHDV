package com.smilecare.booking_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingHistoryResponse {
    private Integer id;
    private LocalDate dateBooking;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String status;
    private String description; // <--- ĐÃ THÊM TRƯỜNG NÀY
    private String doctorName;
    private Long totalPrice;
    private List<String> serviceNames;

    // Constructor mặc định (Cần thiết cho Jackson)
    public BookingHistoryResponse() {}

    // Constructor đầy đủ (Đã thêm description vào vị trí thứ 6 cho khớp với Controller)
    public BookingHistoryResponse(Integer id, LocalDate dateBooking, LocalTime timeStart, LocalTime timeEnd, String status, String description, String doctorName, Long totalPrice, List<String> serviceNames) {
        this.id = id;
        this.dateBooking = dateBooking;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.status = status;
        this.description = description; // <--- Đã gán dữ liệu
        this.doctorName = doctorName;
        this.totalPrice = totalPrice;
        this.serviceNames = serviceNames;
    }

    // Getters và Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDateBooking() { return dateBooking; }
    public void setDateBooking(LocalDate dateBooking) { this.dateBooking = dateBooking; }

    public LocalTime getTimeStart() { return timeStart; }
    public void setTimeStart(LocalTime timeStart) { this.timeStart = timeStart; }

    public LocalTime getTimeEnd() { return timeEnd; }
    public void setTimeEnd(LocalTime timeEnd) { this.timeEnd = timeEnd; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Đã thêm Getter/Setter cho description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public Long getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Long totalPrice) { this.totalPrice = totalPrice; }

    public List<String> getServiceNames() { return serviceNames; }
    public void setServiceNames(List<String> serviceNames) { this.serviceNames = serviceNames; }
}