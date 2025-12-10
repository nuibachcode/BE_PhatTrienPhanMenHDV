package com.smilecare.booking_service.controller;

import com.smilecare.booking_service.dto.ApiResponse;
import com.smilecare.booking_service.dto.BookingRequestDTO;
import com.smilecare.booking_service.dto.BookingResponseDTO;
import com.smilecare.booking_service.dto.BookingHistoryResponse;
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate; // <--- Import này quan trọng để nhận tham số Date
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // --- API 1: Lấy tất cả ---
    @GetMapping
    public ApiResponse<List<Booking>> getAllBookings() {
        try {
            return ApiResponse.success(bookingService.getAllBookings(), "Lấy danh sách thành công");
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi: " + e.getMessage());
        }
    }

    // --- API 2: Lấy 1 cái theo ID ---
    @GetMapping("/{id}")
    public ApiResponse<BookingResponseDTO> getBookingById(@PathVariable Integer id) {
        BookingResponseDTO result = bookingService.getBookingById(id);

        if (result != null) {
            return ApiResponse.success(result, "Lấy thông tin thành công");
        } else {
            return ApiResponse.error(1, "Không tìm thấy lịch hẹn ID: " + id);
        }
    }

    // --- API 3: TẠO MỚI ---
    @PostMapping
    public ApiResponse<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        try {
            Booking newBooking = bookingService.createBooking(request);

            BookingResponseDTO response = new BookingResponseDTO();
            response.setBookingId(newBooking.getId());
            response.setStatus(newBooking.getStatus());
            response.setMessage("Đặt lịch thành công!");
            response.setDateBooking(newBooking.getDateBooking());
            response.setTimeStart(newBooking.getTimeStart());

            return ApiResponse.success(response, "Đặt lịch thành công");
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi đặt lịch: " + e.getMessage());
        }
    }

    // --- API 4: LẤY LỊCH SỬ ---
    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<BookingHistoryResponse>> getHistory(@PathVariable Integer patientId) {
        try {
            List<BookingHistoryResponse> history = bookingService.getHistoryByPatientId(patientId);
            return ApiResponse.success(history, "Lấy lịch sử thành công");
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi lấy lịch sử: " + e.getMessage());
        }
    }

    // --- API 5: CẬP NHẬT TRẠNG THÁI ---
    @PutMapping("/{id}")
    public ApiResponse<String> updateBookingStatus(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        try {
            String newStatus = body.get("status");
            boolean isUpdated = bookingService.updateStatus(id, newStatus);

            if (isUpdated) {
                return ApiResponse.success("OK", "Cập nhật trạng thái thành công");
            } else {
                return ApiResponse.error(1, "Không tìm thấy Booking ID: " + id);
            }
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi cập nhật: " + e.getMessage());
        }
    }

    // --- API 6: (MỚI THÊM) LẤY LỊCH KHÁM CHO BÁC SĨ ---
    // URL: /api/bookings/doctor-schedule?doctorId=2&date=2025-12-09
    @GetMapping("/doctor-schedule")
    public ApiResponse<List<BookingResponseDTO>> getDoctorSchedule(
            @RequestParam Integer doctorId,
            @RequestParam LocalDate date) {
        try {
            List<BookingResponseDTO> list = bookingService.getBookingsByDoctorAndDate(doctorId, date);
            return ApiResponse.success(list, "Lấy lịch khám thành công");
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi: " + e.getMessage());
        }
    }
}