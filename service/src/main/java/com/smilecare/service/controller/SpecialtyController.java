package com.smilecare.service.controller;

import com.smilecare.service.entity.Specialty;
import com.smilecare.service.payload.ApiResponse;
import com.smilecare.service.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    public ApiResponse<List<Specialty>> getAll() {
        return new ApiResponse<>("Lấy danh sách chuyên khoa thành công", 0, specialtyService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Specialty> getById(@PathVariable Integer id) {
        Specialty item = specialtyService.getById(id);
        if (item != null) {
            return new ApiResponse<>("Lấy chuyên khoa thành công", 0, item);
        }
        return new ApiResponse<>("Không tìm thấy chuyên khoa", 1, null);
    }

    @PostMapping
    public ApiResponse<Specialty> create(@RequestBody Specialty newItem) {
        try {
            return new ApiResponse<>("Tạo chuyên khoa thành công", 0, specialtyService.create(newItem));
        } catch (Exception e) {
            return new ApiResponse<>("Lỗi tạo chuyên khoa: " + e.getMessage(), 1, null);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Specialty> update(@PathVariable Integer id, @RequestBody Specialty updateData) {
        Specialty updated = specialtyService.update(id, updateData);
        if (updated != null) {
            return new ApiResponse<>("Cập nhật thành công", 0, updated);
        }
        return new ApiResponse<>("Không tìm thấy chuyên khoa để sửa", 1, null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Integer id) {
        if (specialtyService.delete(id)) {
            return new ApiResponse<>("Xóa thành công", 0, null);
        }
        return new ApiResponse<>("Không tìm thấy chuyên khoa để xóa", 1, null);
    }
}