package com.smilecare.booking_service.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequestDTO {
    private Integer doctorId;
    private LocalDate dateWork;  // Spring tự convert chuỗi "YYYY-MM-DD"
    private String timeStart; // <--- Sửa thành String
    private String timeEnd;   // <--- Sửa thành String
    private Integer maxPatient;
    private String description;
}