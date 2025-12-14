package com.smilecare.booking_service.controller;

import com.smilecare.booking_service.dto.*;
import com.smilecare.booking_service.entity.Booking;
import com.smilecare.booking_service.entity.DoctorInfo; // Import Entity DoctorInfo
import com.smilecare.booking_service.entity.Service;
import com.smilecare.booking_service.repository.DoctorInfoRepository; // Import Repo DoctorInfo
import com.smilecare.booking_service.repository.ServiceRepository;
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
    private final ServiceRepository serviceRepository;
    private final DoctorInfoRepository doctorInfoRepository; // Sử dụng Repo này để lấy Bác sĩ + Chuyên khoa

    // --- 1. API LẤY DANH SÁCH (RESOURCE) ---

    // Đổi tên path để tránh trùng với /{id} gây lỗi 400
    @GetMapping("/get-services")
    public ApiResponse<List<Service>> getAllServices() {
        return ApiResponse.success(serviceRepository.findAll(), "Lấy danh sách dịch vụ thành công");
    }

    @GetMapping("/get-doctors")
    public ApiResponse<List<DoctorInfo>> getAllDoctors() {
        // Trả về danh sách DoctorInfo (có chứa specialtyId và User info)
        return ApiResponse.success(doctorInfoRepository.findAll(), "Lấy danh sách bác sĩ thành công");
    }

    // --- 2. CÁC API NGHIỆP VỤ BOOKING (GIỮ NGUYÊN) ---

    @GetMapping
    public ApiResponse<List<Booking>> getAllBookings() {
        return ApiResponse.success(bookingService.getAllBookings(), "Thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<BookingResponseDTO> getBookingById(@PathVariable Integer id) {
        return ApiResponse.success(bookingService.getBookingById(id), "Thành công");
    }

    @PostMapping
    public ApiResponse<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        try {
            Booking newBooking = bookingService.createBooking(request);
            BookingResponseDTO response = new BookingResponseDTO();
            response.setBookingId(newBooking.getId());
            return ApiResponse.success(response, "Đặt lịch thành công");
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi: " + e.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<BookingHistoryResponse>> getHistory(@PathVariable Integer patientId) {
        return ApiResponse.success(bookingService.getHistoryByPatientId(patientId), "Thành công");
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateBookingStatus(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        bookingService.updateStatus(id, body.get("status"));
        return ApiResponse.success("OK", "Cập nhật thành công");
    }

    @GetMapping("/doctor-schedule")
    public ApiResponse<List<BookingResponseDTO>> getDoctorSchedule(@RequestParam Integer doctorId, @RequestParam LocalDate date) {
        return ApiResponse.success(bookingService.getBookingsByDoctorAndDate(doctorId, date), "Thành công");
    }

    @GetMapping("/doctor-patients")
    public ApiResponse<List<PatientRecordResponse>> getDoctorPatients(@RequestParam Integer doctorId) {
        try {
            return ApiResponse.success(bookingService.getPatientsForDoctor(doctorId), "Thành công");
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi: " + e.getMessage());
        }
    }
}