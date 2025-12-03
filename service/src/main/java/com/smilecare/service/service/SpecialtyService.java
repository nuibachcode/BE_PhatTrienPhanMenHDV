package com.smilecare.service.service;

import com.smilecare.service.entity.Specialty;
import com.smilecare.service.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    // 1. Lấy tất cả
    public List<Specialty> getAll() {
        return specialtyRepository.findAll();
    }

    // 2. Lấy chi tiết theo ID
    public Specialty getById(Integer id) {
        return specialtyRepository.findById(id).orElse(null);
    }

    // 3. Tạo mới
    public Specialty create(Specialty data) {
        return specialtyRepository.save(data);
    }

    // 4. Cập nhật
    public Specialty update(Integer id, Specialty newData) {
        Optional<Specialty> existingOpt = specialtyRepository.findById(id);
        if (existingOpt.isPresent()) {
            Specialty existing = existingOpt.get();
            existing.setNameSpecialty(newData.getNameSpecialty());
            existing.setDescription(newData.getDescription());
            // Cột createdAt, updatedAt tự động lo
            return specialtyRepository.save(existing);
        }
        return null;
    }

    // 5. Xóa
    public boolean delete(Integer id) {
        if (specialtyRepository.existsById(id)) {
            specialtyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}