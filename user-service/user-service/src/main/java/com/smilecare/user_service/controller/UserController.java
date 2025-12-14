package com.smilecare.user_service.controller;

import com.smilecare.user_service.dto.request.DoctorInfoRequest;
import com.smilecare.user_service.dto.request.LoginRequest;
import com.smilecare.user_service.dto.request.UserRequestDTO;
import com.smilecare.user_service.dto.response.ApiResponse;
import com.smilecare.user_service.dto.response.DoctorInfoResponse;
import com.smilecare.user_service.dto.response.UserResponseDTO;
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
// @CrossOrigin(origins = "*") // Không cần thiết nếu đã cấu hình trong Gateway, nhưng để dev thì cứ giữ
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorInfoService doctorInfoService;

    // --- CÁC API CHO USER MANAGEMENT (Admin Page) ---

    // API lấy danh sách bác sĩ (Booking Service gọi cái này)
    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getListDoctors() {

        Integer doctorRoleId = 2;

        // Gọi service lấy list
        List<UserResponseDTO> listDoctors = userService.getUsersByRoleId(doctorRoleId);

        // Đóng gói vào ApiResponse
        ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>();
        response.setEM("Lấy danh sách bác sĩ thành công");
        response.setEC(0);
        response.setDT(listDoctors);

        return ResponseEntity.ok(response);
    }

    // 1. Lấy tất cả user (GET /api/users)
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAll() {
        // Gateway gọi vào: http://localhost:8080/api/users -> trỏ tới đây
        ApiResponse<List<UserResponseDTO>> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    // 2. Đổi quyền User (PUT /api/users/{id}/role)
    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserRole(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, Integer> payload // Nhận { "roleId": 1 }
    ) {
        Integer newRoleId = payload.get("roleId");
        ApiResponse<UserResponseDTO> response = userService.changeUserRole(id, newRoleId);
        return ResponseEntity.ok(response);
    }

    // 3. Xóa User (DELETE /api/users/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable("id") Integer id) {
        ApiResponse<String> response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

    // --- CÁC API CƠ BẢN (Login/Register/Profile) ---

    // Tạo mới (Register)
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(@RequestBody UserRequestDTO request) {
        ApiResponse<UserResponseDTO> result = userService.createUser(request);
        return result.getEC() == 0 ? ResponseEntity.ok(result) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

    // Xem Profile
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserProfile(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Sửa Profile
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateProfile(
            @PathVariable("id") Integer id,
            @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.updateUserProfile(id, request));
    }

    // --- CÁC API DOCTOR INFO (Giữ nguyên) ---

    @GetMapping("/doctor-info")
    public ResponseEntity<ApiResponse<List<DoctorInfoResponse>>> getAllDoctorInfo() {
        return ResponseEntity.ok(doctorInfoService.getAllDoctorInfo());
    }

    @GetMapping("/doctor-info/{id}")
    public ResponseEntity<ApiResponse<DoctorInfoResponse>> getDoctorInfoById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(doctorInfoService.getDoctorInfoById(id));
    }

    @PutMapping("/doctor-info")
    public ResponseEntity<ApiResponse<?>> updateDoctorInfo(@RequestBody DoctorInfoRequest request) {
        return ResponseEntity.ok(doctorInfoService.updateDoctorInfo(request));
    }

}