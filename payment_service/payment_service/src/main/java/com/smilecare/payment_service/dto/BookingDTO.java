package com.smilecare.payment_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDTO {

    // 1. Booking ID (Hứng trường "id" từ Booking Service)
    @JsonProperty("id")
    private Integer bookingId;

    // 2. Status (Cần để check)
    private String status;

    // 3. 🛑 TRƯỜNG CẦN THIẾT CHO AdminController
    // (Booking Service trả về "patientId" trực tiếp trong Booking Entity)
    private Integer patientId;
}