package com.smilecare.booking_service.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class BookingRequestDTO {
    private Integer patientId;
    private Integer doctorId;
    private Integer scheduleId;

    private LocalDate dateBooking;

    // Spring Boot sẽ tự động convert chuỗi "HH:mm" hoặc "HH:mm:ss" thành LocalTime
    private LocalTime timeStart;
    private LocalTime timeEnd;

    private String description;

    // --- QUAN TRỌNG: Danh sách ID các dịch vụ được chọn ---
    // Frontend gửi lên mảng: serviceIds: [1, 2, 5]
    private List<Integer> serviceIds;

}