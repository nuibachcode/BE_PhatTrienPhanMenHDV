package com.smilecare.service.controller;

import com.smilecare.service.entity.Specialty;
import com.smilecare.service.payload.ApiResponse; // Giả sử ApiResponse nằm trong package payload
import com.smilecare.service.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    // --- 1. Lấy tất cả Chuyên khoa (GET /api/specialties) ---
    @GetMapping
    public ApiResponse<List<Specialty>> handleGetAllSpecialties() {
        List<Specialty> list = specialtyService.getAll();

        // Trả về thành công (EC=0) với danh sách chuyên khoa
        return new ApiResponse<>("Lấy danh sách chuyên khoa thành công", 0, list);
    }

    // --- 2. Lấy chi tiết theo ID (GET /api/specialties/{id}) ---
    @GetMapping("/{id}")
    public ApiResponse<Specialty> handleGetSpecialtyById(@PathVariable Integer id) {
        Specialty specialty = specialtyService.getById(id);

        if (specialty != null) {
            // Trả về thành công (EC=0)
            return new ApiResponse<>("Lấy thông tin chuyên khoa thành công", 0, specialty);
        } else {
            // Trả về lỗi (EC=1) nếu không tìm thấy
            return new ApiResponse<>("Không tìm thấy chuyên khoa", 1, null);
        }
    }

    // --- 3. Tạo mới Chuyên khoa (POST /api/specialties) ---
    @PostMapping
    public ApiResponse<Specialty> handleCreateSpecialty(@RequestBody Specialty newItem) {
        // Kiểm tra dữ liệu đầu vào cơ bản
        if (newItem.getNameSpecialty() == null || newItem.getNameSpecialty().trim().isEmpty()) {
            return new ApiResponse<>("Tên chuyên khoa không được để trống", 1, null);
        }

        try {
            Specialty createdSpecialty = specialtyService.create(newItem);
            // Trả về thành công (EC=0)
            return new ApiResponse<>("Tạo chuyên khoa mới thành công", 0, createdSpecialty);
        } catch (Exception e) {
            // Xử lý lỗi trong quá trình tạo
            return new ApiResponse<>("Lỗi khi tạo chuyên khoa: " + e.getMessage(), 1, null);
        }
    }

    // --- 4. Cập nhật Chuyên khoa (PUT /api/specialties/{id}) ---
    @PutMapping("/{id}")
    public ApiResponse<Specialty> handleUpdateSpecialty(
            @PathVariable Integer id,
            @RequestBody Specialty updateData) {

        Specialty updatedSpecialty = specialtyService.update(id, updateData);

        if (updatedSpecialty != null) {
            // Trả về thành công (EC=0)
            return new ApiResponse<>("Cập nhật chuyên khoa thành công", 0, updatedSpecialty);
        } else {
            // Trả về lỗi (EC=1) nếu không tìm thấy ID
            return new ApiResponse<>("Không tìm thấy chuyên khoa để sửa", 1, null);
        }
    }

    // --- 5. Xóa Chuyên khoa (DELETE /api/specialties/{id}) ---
    @DeleteMapping("/{id}")
    public ApiResponse<String> handleDeleteSpecialty(@PathVariable Integer id) {
        boolean isDeleted = specialtyService.delete(id);

        if (isDeleted) {
            // Trả về thành công (EC=0)
            return new ApiResponse<>("Xóa chuyên khoa thành công", 0, null);
        } else {
            // Trả về lỗi (EC=1) nếu không tìm thấy hoặc lỗi xóa
            return new ApiResponse<>("Không tìm thấy chuyên khoa hoặc lỗi khi xóa", 1, null);
        }
    }
}