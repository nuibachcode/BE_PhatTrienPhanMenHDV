// src/main/java/com/smilecare/user_service/service/SpecialtyService.java
package com.smilecare.user_service.service;

import com.smilecare.user_service.dto.response.ApiResponse;
import com.smilecare.user_service.dto.response.SpecialtyResponseDTO; // DTO đơn giản cho Specialty
import com.smilecare.user_service.entity.Specialty;
import com.smilecare.user_service.repository.SpecialtyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ApiResponse<List<SpecialtyResponseDTO>> getAllSpecialties() {
        try {
            List<Specialty> specialties = specialtyRepository.findAll();
            List<SpecialtyResponseDTO> dtos = specialties.stream()
                    .map(spec -> modelMapper.map(spec, SpecialtyResponseDTO.class))
                    .collect(Collectors.toList());

            return ApiResponse.success(dtos, "Lấy danh sách thành công");
        } catch (Exception e) {
            return ApiResponse.error(-1, "Lỗi từ server.");
        }
    }
}