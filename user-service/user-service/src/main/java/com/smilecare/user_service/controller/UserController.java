package com.smilecare.user_service.controller;

import com.smilecare.user_service.dto.request.LoginRequest;
import com.smilecare.user_service.dto.request.UserRequestDTO;
import com.smilecare.user_service.dto.request.DoctorInfoRequest; // Cần import DoctorInfoRequest
import com.smilecare.user_service.dto.response.ApiResponse;
import com.smilecare.user_service.dto.response.UserResponseDTO;
import com.smilecare.user_service.dto.response.DoctorInfoResponse;
import com.smilecare.user_service.service.DoctorInfoService;
import com.smilecare.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ⭐ TIÊM DOCTORINFOSERVICE ĐỂ DÙNG CHUNG ENDPOINT
    @Autowired
    private DoctorInfoService doctorInfoService;

    // --- 1. USER: CRUD Cơ bản & Đăng ký/Đăng nhập ---

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(@RequestBody UserRequestDTO request) {
        ApiResponse<UserResponseDTO> result = userService.createUser(request);
        if (result.getEC() == 0) {
            return ResponseEntity.ok(result);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody LoginRequest request) {
        ApiResponse<Map<String, Object>> result = userService.loginUser(request);
        return ResponseEntity.ok(result);
    }

    // Lấy thông tin User Profile theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserProfile(@PathVariable("id") Integer id) {
        ApiResponse<UserResponseDTO> response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // Cập nhật thông tin User Profile theo ID
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateProfile(
            @PathVariable("id") Integer id,
            @RequestBody UserRequestDTO request) {
        ApiResponse<?> response = userService.updateUserProfile(id, request);
        return ResponseEntity.ok(response);
    }

    // --- 2. DOCTORINFO: Lấy/Cập nhật Hồ sơ Bác sĩ ---

    /**
     * Endpoint: GET /api/users/doctor-info (Lấy danh sách tất cả bác sĩ)
     */
    @GetMapping("/doctor-info")
    public ResponseEntity<ApiResponse<List<DoctorInfoResponse>>> getAllDoctorInfo() {
        ApiResponse<List<DoctorInfoResponse>> response = doctorInfoService.getAllDoctorInfo();
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint: GET /api/users/doctor-info/{id} (Lấy hồ sơ bác sĩ theo ID)
     * Frontend gọi: axios.get(`http://localhost:8081/api/doctor-info/${user.id}`)
     * LƯU Ý: Phải đổi path thành /api/users/doctor-info/{id} trong Frontend
     */
    @GetMapping("/doctor-info/{id}")
    public ResponseEntity<ApiResponse<DoctorInfoResponse>> getDoctorInfoById(@PathVariable("id") Integer id) {
        // ID bác sĩ là Integer (đã đồng bộ với Entity User)
        ApiResponse<DoctorInfoResponse> response = doctorInfoService.getDoctorInfoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint: PUT /api/users/doctor-info (Cập nhật thông tin chi tiết)
     * Frontend gọi: axios.put("http://localhost:8080/api/doctor-info")
     * LƯU Ý: Phải đổi path thành /api/users/doctor-info trong Frontend
     */
    @PutMapping("/doctor-info")
    public ResponseEntity<ApiResponse<?>> updateDoctorInfo(@RequestBody DoctorInfoRequest request) {
        ApiResponse<?> response = doctorInfoService.updateDoctorInfo(request);
        return ResponseEntity.ok(response);
    }

}