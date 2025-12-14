package com.smilecare.booking_service.service;

import com.smilecare.booking_service.dto.ScheduleRequestDTO;
import com.smilecare.booking_service.entity.Schedule;
import com.smilecare.booking_service.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    // 1. Lấy danh sách lịch của Bác sĩ
    public List<Schedule> getSchedulesByDoctor(Integer doctorId) {
        return scheduleRepository.findByDoctorIdOrderByDateWorkAsc(doctorId);
    }

    // 2. Tạo lịch mới
    public Schedule createSchedule(ScheduleRequestDTO request) {
        // Có thể thêm validate: Check xem khung giờ có bị trùng không (nâng cao)

        Schedule schedule = new Schedule();
        schedule.setDoctorId(request.getDoctorId());
        schedule.setDateWork(request.getDateWork());
        schedule.setTimeStart(request.getTimeStart());
        schedule.setTimeEnd(request.getTimeEnd());
        schedule.setMaxPatient(request.getMaxPatient());
        schedule.setDescription(request.getDescription());

        return scheduleRepository.save(schedule);
    }

    // 3. Xóa lịch
    public boolean deleteSchedule(Integer id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}