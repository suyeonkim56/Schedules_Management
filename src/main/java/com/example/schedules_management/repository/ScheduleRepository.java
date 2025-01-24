package com.example.schedules_management.repository;

import com.example.schedules_management.dto.ScheduleResponseDto;
import com.example.schedules_management.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Schedule findScheduleByID(Long id);

    void deleteSchedule(Long id);
}
