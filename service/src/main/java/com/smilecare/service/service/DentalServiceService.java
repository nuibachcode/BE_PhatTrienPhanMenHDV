package com.smilecare.service.service;

import com.smilecare.service.entity.DentalService;
import com.smilecare.service.repository.DentalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentalServiceService {

    @Autowired
    private DentalServiceRepository dentalServiceRepository;

    // 1. Lấy tất cả
    public List<DentalService> getAll() {
        return dentalServiceRepository.findAll();
    }

    // 2. Lấy 1 cái theo ID
    public DentalService getById(Integer id) {
        return dentalServiceRepository.findById(id).orElse(null);
    }

    // 3. Tạo mới
    public DentalService create(DentalService data) {
        return dentalServiceRepository.save(data);
    }

    // 4. Cập nhật
    public DentalService update(Integer id, DentalService newData) {
        Optional<DentalService> existingOpt = dentalServiceRepository.findById(id);
        if (existingOpt.isPresent()) {
            DentalService existing = existingOpt.get();
            // Cập nhật từng trường (Frontend gửi cái gì thì update cái đó)
            existing.setNameService(newData.getNameService());
            existing.setPrice(newData.getPrice());
            existing.setDuration(newData.getDuration());
            existing.setDescription(newData.getDescription());
            existing.setSpecialtyId(newData.getSpecialtyId());

            return dentalServiceRepository.save(existing);
        }
        return null; // Không tìm thấy để sửa
    }

    // 5. Xóa
    public boolean delete(Integer id) {
        if (dentalServiceRepository.existsById(id)) {
            dentalServiceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}