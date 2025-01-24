package com.example.schedules_management.service;

import com.example.schedules_management.dto.ScheduleRequestDto;
import com.example.schedules_management.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto findScheduleByID(Long id);

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedules();

    ScheduleResponseDto updateSchedule(Long id, String contents, String writer,String password);

    void deleteSchedule(Long id, String password);
}

