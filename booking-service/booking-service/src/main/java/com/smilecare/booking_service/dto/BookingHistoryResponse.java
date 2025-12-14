package com.smilecare.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingHistoryResponse {
    private Integer id;
    private LocalDate dateBooking;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String status;
    private String description;

    private String doctorName; // Tên bác sĩ
    private Long totalAmount;  // Tổng tiền
    private List<String> serviceNames; // Danh sách tên dịch vụ
}