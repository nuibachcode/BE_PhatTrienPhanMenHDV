package com.smilecare.booking_service.controller;

import com.smilecare.booking_service.dto.ApiResponse;
import com.smilecare.booking_service.dto.ScheduleRequestDTO;
import com.smilecare.booking_service.entity.Schedule;
import com.smilecare.booking_service.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // 1. API: Lấy danh sách lịch của bác sĩ
    // GET /api/schedules/doctor?doctorId=1
    @GetMapping("/doctor")
    public ApiResponse<List<Schedule>> getDoctorSchedules(@RequestParam Integer doctorId) {
        try {
            List<Schedule> list = scheduleService.getSchedulesByDoctor(doctorId);
            return ApiResponse.success(list, "Lấy danh sách lịch thành công");
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi: " + e.getMessage());
        }
    }

    // 2. API: Tạo lịch mới
    // POST /api/schedules
    @PostMapping
    public ApiResponse<Schedule> createSchedule(@RequestBody ScheduleRequestDTO request) {
        try {
            Schedule newSchedule = scheduleService.createSchedule(request);
            return ApiResponse.success(newSchedule, "Tạo lịch thành công");
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi tạo lịch: " + e.getMessage());
        }
    }

    // 3. API: Xóa lịch
    // DELETE /api/schedules/{id}
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSchedule(@PathVariable Integer id) {
        try {
            boolean isDeleted = scheduleService.deleteSchedule(id);
            if (isDeleted) {
                return ApiResponse.success("OK", "Xóa lịch thành công");
            } else {
                return ApiResponse.error(1, "Không tìm thấy lịch để xóa");
            }
        } catch (Exception e) {
            return ApiResponse.error(1, "Lỗi xóa lịch: " + e.getMessage());
        }
    }
}