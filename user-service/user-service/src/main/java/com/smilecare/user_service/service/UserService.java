package com.smilecare.user_service.service;

import com.smilecare.user_service.dto.request.LoginRequest;
import com.smilecare.user_service.dto.request.UserRequestDTO;
import com.smilecare.user_service.dto.response.ApiResponse;
import com.smilecare.user_service.dto.response.UserResponseDTO;
import com.smilecare.user_service.entity.Role;
import com.smilecare.user_service.entity.User;
import com.smilecare.user_service.repository.RoleRepository;
import com.smilecare.user_service.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Integer DEFAULT_PATIENT_ROLE_ID = 3;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- HÀM HỖ TRỢ CONVERT DTO ---
    private UserResponseDTO convertToDTO(User user) {
        // Map thủ công để đảm bảo lấy đúng roleId và roleName
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setAccount(user.getAccount());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());

        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getId());       // Quan trọng cho Badge màu ở FE
            dto.setRoleName(user.getRole().getNameRole());
        } else {
            dto.setRoleId(0);
            dto.setRoleName("Unknown");
        }
        return dto;
    }

    // --- 1. ĐĂNG KÝ (REGISTER) ---
    @Transactional
    public ApiResponse<UserResponseDTO> createUser(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.error(1, "Email này đã được sử dụng!");
        }
        if (userRepository.findByAccount(request.getAccount()).isPresent()) {
            return ApiResponse.error(1, "Tên tài khoản này đã tồn tại!");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            return ApiResponse.error(1, "Số điện thoại này đã được sử dụng!");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setAccount(request.getAccount() != null ? request.getAccount() : request.getEmail());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassWord(hashedPassword);

        Optional<Role> roleOptional = roleRepository.findById(DEFAULT_PATIENT_ROLE_ID);
        if (roleOptional.isEmpty()) {
            return ApiResponse.error(2, "Lỗi hệ thống: Role PATIENT không tồn tại!");
        }
        user.setRole(roleOptional.get());

        User savedUser = userRepository.save(user);
        return ApiResponse.success(convertToDTO(savedUser), "Đăng ký tài khoản thành công!");
    }

    // --- 2. ĐĂNG NHẬP (LOGIN) ---
    public ApiResponse<Map<String, Object>> loginUser(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByAccountOrEmail(request.getAccount(), request.getAccount());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
                Map<String, Object> data = new HashMap<>();
                data.put("accessToken", "dummy-token-jwt-example-123456"); // Cần thay bằng JWT thật
                data.put("user", convertToDTO(user));
                return ApiResponse.success(data, "Đăng nhập thành công");
            }
        }
        return ApiResponse.error(1, "Tài khoản hoặc mật khẩu không chính xác!");
    }

    // --- 3. LẤY USER THEO ID ---
    public ApiResponse<UserResponseDTO> getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ApiResponse.error(1, "Người dùng không tồn tại.");
        }
        return ApiResponse.success(convertToDTO(user), "Lấy thông tin thành công.");
    }

    // --- 4. CẬP NHẬT PROFILE ---
    @Transactional
    public ApiResponse<?> updateUserProfile(Integer userId, UserRequestDTO request) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ApiResponse.error(1, "Người dùng không tồn tại.");
        }

        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAddress() != null) user.setAddress(request.getAddress());

        userRepository.save(user);
        return ApiResponse.success("Cập nhật thông tin thành công!");
    }

    // =========================================================================
    // PHẦN MỚI: CHO TRANG QUẢN LÝ USER (ADMIN)
    // =========================================================================

    // --- 5. LẤY DANH SÁCH TẤT CẢ USER (Bọc trong ApiResponse) ---
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserResponseDTO> dtos = users.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            // Trả về ApiResponse chuẩn format {EC: 0, EM: "...", DT: [...]}
            return ApiResponse.success(dtos, "Lấy danh sách người dùng thành công");
        } catch (Exception e) {
            return ApiResponse.error(-1, "Lỗi server khi lấy danh sách user");
        }
    }

    // --- 6. ĐỔI QUYỀN (ROLE) USER ---
    public ApiResponse<UserResponseDTO> changeUserRole(Integer userId, Integer newRoleId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) return ApiResponse.error(1, "User không tồn tại");

            Role role = roleRepository.findById(newRoleId).orElse(null);
            if (role == null) return ApiResponse.error(1, "Role không tồn tại");

            user.setRole(role);
            User updatedUser = userRepository.save(user);

            return ApiResponse.success(convertToDTO(updatedUser), "Cập nhật quyền thành công!");
        } catch (Exception e) {
            return ApiResponse.error(-1, "Lỗi server khi đổi quyền");
        }
    }

    // --- 7. XÓA USER ---
    public ApiResponse<String> deleteUser(Integer userId) {
        try {
            if (!userRepository.existsById(userId)) {
                return ApiResponse.error(1, "User không tồn tại");
            }
            userRepository.deleteById(userId);
            return ApiResponse.success("Xóa user thành công!");
        } catch (Exception e) {
            return ApiResponse.error(-1, "Không thể xóa user (có thể do ràng buộc dữ liệu)");
        }
    }

    // Trong file UserService.java
    public List<UserResponseDTO> getUsersByRoleId(Integer roleId) {
        List<User> users = userRepository.findByRoleId(roleId);

        return users.stream().map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setFullName(user.getFullName());
            dto.setPhone(user.getPhone());
            dto.setEmail(user.getEmail());
            dto.setAddress(user.getAddress());

            // --- SỬA LỖI TẠI ĐÂY ---
            // Thay vì user.getRoleId(), hãy gọi qua object Role
            if (user.getRole() != null) {
                dto.setRoleId(user.getRole().getId());
                // Nếu UserResponseDTO có trường roleName thì set luôn:
                // dto.setRoleName(user.getRole().getRoleName());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}