package com.smilecare.service.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    @JsonProperty("EM")
    private String EM;

    @JsonProperty("EC")
    private int EC;

    @JsonProperty("DT")
    private T DT;

    // --- VIẾT TAY HÀM NÀY VÀO (Thay thế @AllArgsConstructor) ---
    public ApiResponse(String EM, int EC, T DT) {
        this.EM = EM;
        this.EC = EC;
        this.DT = DT;
    }
}