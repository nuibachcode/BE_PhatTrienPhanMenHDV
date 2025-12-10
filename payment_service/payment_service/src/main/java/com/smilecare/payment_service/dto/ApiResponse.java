package com.smilecare.payment_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    @JsonProperty("EC")
    private int EC;

    @JsonProperty("EM")
    private String EM;

    @JsonProperty("DT")
    private T DT;


    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(0, message, data);
    }

    public static <T> ApiResponse<T> error(int errorCode, String errorMessage) {
        return new ApiResponse<>(errorCode, errorMessage, null);
    }
}