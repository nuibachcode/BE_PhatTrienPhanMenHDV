package com.smilecare.booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    @JsonProperty("EM") // Error Message
    private String EM;

    @JsonProperty("EC") // Error Code (0: Success, != 0: Error)
    private int EC;

    @JsonProperty("DT") // Data Transfer
    private T DT;

    // --- THÊM 2 HÀM NÀY ĐỂ GIỐNG PAYMENT SERVICE VÀ TIỆN DỤNG HƠN ---

    /**
     * Helper tạo phản hồi THÀNH CÔNG (EC = 0)
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(message, 0, data);
    }

    /**
     * Helper tạo phản hồi THẤT BẠI (EC != 0)
     */
    public static <T> ApiResponse<T> error(int errorCode, String errorMessage) {
        return new ApiResponse<>(errorMessage, errorCode, null);
    }
}