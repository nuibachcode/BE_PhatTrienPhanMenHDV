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
import org.springframework.security.crypto.password.PasswordEncoder; // CẦN THIẾT
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

    // ⭐ ĐÃ THÊM: TIÊM PasswordEncoder (Giải quyết lỗi "Cannot resolve symbol")
    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- HÀM HỖ TRỢ ---
    private UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getAccount(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole() != null ? user.getRole().getNameRole() : "Unknown",
                user.getRole() != null ? user.getRole().getId() : 0
        );
    }

    // --- HÀM TẠO USER MỚI (REGISTER) ---
    @Transactional
    public ApiResponse<UserResponseDTO> createUser(UserRequestDTO request) {

        // 1. Kiểm tra trùng lặp: Email, Username, Phone
        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.error(1, "Email này đã được sử dụng!");
        }
        if (userRepository.findByAccount(request.getAccount()).isPresent()) {
            return ApiResponse.error(1, "Tên tài khoản này đã tồn tại!");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            return ApiResponse.error(1, "Số điện thoại này đã được sử dụng!");
        }

        // 2. Chuyển DTO -> Entity và set các trường
        User user = new User();
        user.setFullName(request.getFullName());
        user.setAccount(request.getAccount() != null ? request.getAccount() : request.getEmail());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        // 3. BĂM MẬT KHẨU
        // Đã sửa lỗi: Dùng passwordEncoder.encode()
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassWord(hashedPassword);

        // 4. Tìm Role (Mặc định là PATIENT)
        Optional<Role> roleOptional = roleRepository.findById(DEFAULT_PATIENT_ROLE_ID);

        if (roleOptional.isEmpty()) {
            return ApiResponse.error(2, "Lỗi hệ thống: Role PATIENT không tồn tại!");
        }
        user.setRole(roleOptional.get());

        // 5. Lưu vào Database
        User savedUser = userRepository.save(user);

        // 6. Trả về kết quả thành công (Loại bỏ mật khẩu qua DTO)
        UserResponseDTO responseDTO = convertToDTO(savedUser);
        return ApiResponse.success(responseDTO, "Đăng ký tài khoản thành công!");
    }

    // --- HÀM LOGIC LOGIN ---
    public ApiResponse<Map<String, Object>> loginUser(LoginRequest request) {

        // 1. Tìm user theo Account hoặc Email (Giả định hàm findByAccountOrEmail tồn tại)
            Optional<User> userOptional = userRepository.findByAccountOrEmail(request.getAccount(), request.getAccount());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 2. ĐỐI CHIẾU MẬT KHẨU
            // Đã sửa lỗi: Dùng passwordEncoder.matches()
            if (passwordEncoder.matches(request.getPassword(), user.getPassWord())) {

                // 3. Đăng nhập thành công

                // Chuẩn bị dữ liệu trả về (Data)
                Map<String, Object> data = new HashMap<>();
                data.put("accessToken", "dummy-token-jwt-example-123456"); // Giả lập Token
                data.put("user", convertToDTO(user));

                // Trả về ApiResponse thành công
                return ApiResponse.success(data, "Đăng nhập thành công");
            }
        }

        // Đăng nhập thất bại (Account không tồn tại HOẶC Mật khẩu không khớp)
        return ApiResponse.error(1, "Tài khoản hoặc mật khẩu không chính xác!");
    }

    // --- HÀM GET USER BY ID ---
    public ApiResponse<UserResponseDTO> getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ApiResponse.error(1, "Người dùng không tồn tại.");
        }

        UserResponseDTO responseDTO =modelMapper.map(user, UserResponseDTO.class);
        return ApiResponse.success(responseDTO, "Lấy thông tin người dùng thành công.");
    }

    // --- HÀM CẬP NHẬT PROFILE ---
    @Transactional
    public ApiResponse<?> updateUserProfile(Integer userId, UserRequestDTO request) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ApiResponse.error(1, "Người dùng không tồn tại.");
        }

        // Cập nhật các trường được phép sửa
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        userRepository.save(user);

        return ApiResponse.success("Cập nhật thông tin thành công!");
    }

    // --- HÀM GET ALL USERS ---
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}