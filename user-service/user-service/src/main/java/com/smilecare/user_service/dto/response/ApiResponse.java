package com.smilecare.user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    // Các trường đã có
    @JsonProperty("EC")
    private int EC;

    @JsonProperty("EM")
    private String EM;

    @JsonProperty("DT")
    private T DT;

    // ------------------------------------------------------------------
    // THÊM CÁC PHƯƠNG THỨC STATIC FACTORY METHODS DƯỚI ĐÂY
    // ------------------------------------------------------------------

    /**
     * Phương thức tạo đối tượng ApiResponse thành công (EC=0).
     * @param data Dữ liệu trả về (DT)
     * @param message Thông báo thành công (EM)
     * @return Đối tượng ApiResponse
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(0, message, data); // EC = 0 cho thành công
    }

    /**
     * Phương thức tạo đối tượng ApiResponse thành công không có dữ liệu trả về (ví dụ: Update/Delete).
     * @param message Thông báo thành công (EM)
     * @return Đối tượng ApiResponse
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(0, message, null); // DT là null
    }

    /**
     * Phương thức tạo đối tượng ApiResponse thất bại/lỗi.
     * @param errorCode Mã lỗi (EC)
     * @param errorMessage Thông báo lỗi (EM)
     * @return Đối tượng ApiResponse
     */
    public static <T> ApiResponse<T> error(int errorCode, String errorMessage) {
        return new ApiResponse<>(errorCode, errorMessage, null); // DT là null khi lỗi
    }
}