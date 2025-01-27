package com.example.schedules_management.repository;

import com.example.schedules_management.dto.ScheduleResponseDto;
import com.example.schedules_management.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Optional<Schedule> findScheduleByID(Long id);

    int updateSchedule(Long id, String writer, String contents, String password);

    int deleteSchedule(Long id, String password);
}
