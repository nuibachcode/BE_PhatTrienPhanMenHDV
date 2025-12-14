package com.smilecare.booking_service.controller;

import com.smilecare.booking_service.dto.*;
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/get-services")
    public ApiResponse<List<ServiceDTO>> getAllServices() {
        // Dùng hàm static success mới
        return ApiResponse.success(bookingService.getAllServicesFromRemote(), "Lấy danh sách dịch vụ thành công");
    }

    @GetMapping("/get-doctors")
    public ApiResponse<List<DoctorDTO>> getAllDoctors() {
        return ApiResponse.success(bookingService.getAllDoctorsFromRemote(), "Lấy danh sách bác sĩ thành công");
    }

    @GetMapping
    public ApiResponse<List<Booking>> getAllBookings() {
        return ApiResponse.success(bookingService.getAllBookings(), "Thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<BookingResponseDTO> getBookingById(@PathVariable Integer id) {
        BookingResponseDTO res = bookingService.getBookingById(id);
        if (res != null) return ApiResponse.success(res, "Thành công");
        return ApiResponse.error(1, "Không tìm thấy booking");
    }

    @PostMapping
    public ApiResponse<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        try {
            return ApiResponse.success(bookingService.createBooking(request), "Đặt lịch thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(1, "Lỗi: " + e.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<BookingHistoryResponse>> getHistory(@PathVariable Integer patientId) {
        return ApiResponse.success(bookingService.getHistoryByPatientId(patientId), "Thành công");
    }

    // Trong BookingController.java, tìm và thay thế hàm updateBookingStatus
    @PutMapping("/{id}")
    public ApiResponse<String> updateBookingStatus(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        try {
            String newStatus = body.get("status");

            // Log báo hiệu đã nhận request
            System.out.println(">>> [BOOKING-CONTROLLER] Nhận yêu cầu UPDATE ID " + id + " -> " + newStatus);

            boolean ok = bookingService.updateStatus(id, newStatus);

            // Log báo hiệu xử lý thành công
            System.out.println(">>> [BOOKING-CONTROLLER] Xử lý Booking ID " + id + ": " + (ok ? "THÀNH CÔNG" : "THẤT BẠI"));

            return ok ? ApiResponse.success("OK", "Cập nhật thành công") : ApiResponse.error(1, "Không tìm thấy booking hoặc thất bại");

        } catch (Exception e) {
            // BẮT LỖI 500 VÀ IN STACK TRACE ĐỂ TÌM NGUYÊN NHÂN
            System.err.println(">>> [BOOKING-CONTROLLER ERROR] Lỗi 500 xảy ra khi update status Booking ID: " + id);
            e.printStackTrace();

            // Trả về 500 để Payment Service biết
            return ApiResponse.error(500, "Lỗi Server nội bộ khi cập nhật trạng thái: " + e.getMessage());
        }
    }

    @GetMapping("/doctor-schedule")
    public ApiResponse<List<BookingResponseDTO>> getDoctorSchedule(@RequestParam Integer doctorId, @RequestParam LocalDate date) {
        return ApiResponse.success(bookingService.getBookingsByDoctorAndDate(doctorId, date), "Thành công");
    }

    @GetMapping("/doctor-patients")
    public ApiResponse<List<PatientRecordResponse>> getDoctorPatients(@RequestParam Integer doctorId) {
        return ApiResponse.success(bookingService.getPatientsForDoctor(doctorId), "Thành công");
    }
}