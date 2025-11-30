package com.smilecare.user_service.service;

import com.smilecare.user_service.dto.response.ApiResponse;
import com.smilecare.user_service.dto.response.DoctorInfoResponse;
import com.smilecare.user_service.dto.request.DoctorInfoRequest; // ⭐ Cần thiết
import com.smilecare.user_service.entity.DoctorInfo;
import com.smilecare.user_service.entity.User; // ⭐ Cần thiết
import com.smilecare.user_service.entity.Specialty; // ⭐ Cần thiết
import com.smilecare.user_service.repository.DoctorInfoRepository;
import com.smilecare.user_service.repository.UserRepository; // ⭐ Cần thiết
import com.smilecare.user_service.repository.SpecialtyRepository; // ⭐ Cần thiết

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Cần thiết cho @Transactional

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorInfoService {

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;
    @Autowired
    private UserRepository userRepository; // ⭐ Đã thêm ⭐
    @Autowired
    private SpecialtyRepository specialtyRepository; // ⭐ Đã thêm ⭐
    @Autowired
    private ModelMapper modelMapper;

    // --- 1. LẤY TẤT CẢ THÔNG TIN BÁC SĨ (GET /api/users/doctor-info) ---
    public ApiResponse<List<DoctorInfoResponse>> getAllDoctorInfo() {
        try {
            List<DoctorInfo> doctors = doctorInfoRepository.findAll();

            List<DoctorInfoResponse> responseList = doctors.stream()
                    .map(doctorInfo -> modelMapper.map(doctorInfo, DoctorInfoResponse.class))
                    .collect(Collectors.toList());

            return ApiResponse.success(responseList, "Lấy danh sách bác sĩ thành công");

        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách bác sĩ: " + e.getMessage());
            return ApiResponse.error(1, "Lấy không thành công danh sách bác sĩ");
        }
    }

    // --- 2. LẤY THÔNG TIN CHI TIẾT BÁC SĨ THEO ID (GET /api/users/doctor-info/{id}) ---
    // ⭐ Đã sửa Long thành Integer ⭐
    public ApiResponse<DoctorInfoResponse> getDoctorInfoById(Integer doctorId) {

        Optional<DoctorInfo> optionalInfo = doctorInfoRepository.findByDoctor_Id(doctorId);

        if (optionalInfo.isEmpty()) {

            // Để khớp logic Frontend, ta trả về thông tin User cơ bản nếu DoctorInfo không tồn tại
            Optional<User> user = userRepository.findById(doctorId);
            if (user.isPresent()) {
                DoctorInfoResponse response = new DoctorInfoResponse();

                // ⭐ Đảm bảo ModelMapper ánh xạ User Entity sang UserDTO ⭐
                response.setDoctor(modelMapper.map(user.get(), DoctorInfoResponse.UserDTO.class));
                return ApiResponse.success(response, "Chưa có thông tin chuyên môn chi tiết.");
            }

            return ApiResponse.error(2, "Không tìm thấy thông tin bác sĩ.");
        }

        DoctorInfo info = optionalInfo.get();
        DoctorInfoResponse response = modelMapper.map(info, DoctorInfoResponse.class);

        return ApiResponse.success(response, "Lấy thông tin bác sĩ thành công");
    }

    // --- 3. CẬP NHẬT/TẠO MỚI THÔNG TIN BÁC SĨ (PUT /api/users/doctor-info) ---
    @Transactional
    public ApiResponse<?> updateDoctorInfo(DoctorInfoRequest request) {
        if (request.getDoctorId() == null) {
            return ApiResponse.error(1, "Thiếu tham số doctorId");
        }

        // 1. TÌM/TẠO DoctorInfo (Upsert Logic)
        Optional<DoctorInfo> existingInfo = doctorInfoRepository.findByDoctor_Id(request.getDoctorId());

        // Lấy User (Bác sĩ)
        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User/Doctor"));

        // Lấy Specialty
        // ⭐ specialtyId là Integer ⭐
        Specialty specialty = specialtyRepository.findById(request.getSpecialtyId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Specialty"));

        DoctorInfo infoToSave = existingInfo.orElseGet(DoctorInfo::new);

        // Cập nhật các trường DoctorInfo
        infoToSave.setDoctor(doctor);
        infoToSave.setSpecialty(specialty);
        infoToSave.setLever(request.getLever());
        infoToSave.setBio(request.getBio());

        doctorInfoRepository.save(infoToSave);

        // 2. CẬP NHẬT bảng User (Thông tin cá nhân)
        if (request.getPhone() != null) doctor.setPhone(request.getPhone());
        if (request.getAddress() != null) doctor.setAddress(request.getAddress());
        if (request.getFullName() != null) doctor.setFullName(request.getFullName());

        userRepository.save(doctor); // Lưu thông tin User đã thay đổi

        return ApiResponse.success("Cập nhật thông tin thành công");
    }
}