package com.smilecare.service.controller;

import com.smilecare.service.entity.DentalService;
import com.smilecare.service.payload.ApiResponse;
import com.smilecare.service.service.DentalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services") 
public class DentalServiceController {

    @Autowired
    private DentalServiceService dentalServiceService;

    @GetMapping
    public ApiResponse<List<DentalService>> handleGetAllServices() {
        List<DentalService> list = dentalServiceService.getAll();

        return new ApiResponse<>("Lấy danh sách dịch vụ thành công", 0, list);
    }


    @GetMapping("/{id}")
    public ApiResponse<DentalService> handleGetServiceById(@PathVariable Integer id) {
        DentalService service = dentalServiceService.getById(id);

        if (service != null) {
            return new ApiResponse<>("Lấy thông tin dịch vụ thành công", 0, service);
        } else {
            return new ApiResponse<>("Không tìm thấy dịch vụ", 1, null);
        }
    }


    @PostMapping
    public ApiResponse<DentalService> handleCreateService(@RequestBody DentalService newItem) {
        try {
            DentalService createdService = dentalServiceService.create(newItem);
            return new ApiResponse<>("Tạo dịch vụ mới thành công", 0, createdService);
        } catch (Exception e) {
            return new ApiResponse<>("Lỗi khi tạo dịch vụ: " + e.getMessage(), 1, null);
        }
    }


    @PutMapping("/{id}")
    public ApiResponse<DentalService> handleUpdateService(@PathVariable Integer id, @RequestBody DentalService updateData) {
        DentalService updatedService = dentalServiceService.update(id, updateData);

        if (updatedService != null) {
            return new ApiResponse<>("Cập nhật dịch vụ thành công", 0, updatedService);
        } else {
            return new ApiResponse<>("Không tìm thấy dịch vụ để sửa", 1, null);
        }
    }


    @DeleteMapping("/{id}")
    public ApiResponse<String> handleDeleteService(@PathVariable Integer id) {
        boolean isDeleted = dentalServiceService.delete(id);

        if (isDeleted) {
            return new ApiResponse<>("Xóa dịch vụ thành công", 0, null);
        } else {
            return new ApiResponse<>("Không tìm thấy dịch vụ hoặc lỗi khi xóa", 1, null);
        }
    }
}