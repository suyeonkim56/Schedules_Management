package com.example.schedules_management.service;

import com.example.schedules_management.dto.ScheduleRequestDto;
import com.example.schedules_management.dto.ScheduleResponseDto;
import com.example.schedules_management.entity.Schedule;
import com.example.schedules_management.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        // 객체 생성(ID 없음)
        Schedule schedule = new Schedule(requestDto.getWriter(), requestDto.getContents(), requestDto.getPassword());

        //DB 저장
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public ScheduleResponseDto findScheduleByID(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleByID(id);

        //NPL 검증
        if(optionalSchedule.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        return new ScheduleResponseDto(optionalSchedule.get());
    }


    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String contents, String writer, String password) {
        //필수값 검증
        if (contents == null || writer == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contents, writer and password are required values");
        }

        int updatedSchedule = scheduleRepository.updateSchedule(id, contents, writer, password);

        //NPL 검증
        if(updatedSchedule == 0)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id + "OR not equal password.");
        }

        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleByID(id);

        return new ScheduleResponseDto(optionalSchedule.get());
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        int deletedSchedule = scheduleRepository.deleteSchedule(id, password);
        if(deletedSchedule == 0)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password is not equal");
        }
    }
}
