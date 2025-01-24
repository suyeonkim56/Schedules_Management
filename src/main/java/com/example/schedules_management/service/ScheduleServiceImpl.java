package com.example.schedules_management.service;

import com.example.schedules_management.dto.ScheduleRequestDto;
import com.example.schedules_management.dto.ScheduleResponseDto;
import com.example.schedules_management.entity.Schedule;
import com.example.schedules_management.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        Schedule schedule = scheduleRepository.findScheduleByID(id);

        //NPL 검증
        if(schedule == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        return new ScheduleResponseDto(schedule);
    }


    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String contents, String writer, String password) {
        Schedule schedule = scheduleRepository.findScheduleByID(id);

        //NPL 검증
        if(schedule == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        //필수값 검증
        if (contents == null || writer == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contents, writer and password are required values");
        }

        //비밀번호 일치
        if(schedule.getPassword().equals(password))
        {
            schedule.update(contents, writer);
            return new ScheduleResponseDto(schedule);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not equal");
        }
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findScheduleByID(id);

        //NPL 검증
        if(schedule == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        //비밀번호 일치
        if(schedule.getPassword().equals(password))
        {
            scheduleRepository.deleteSchedule(id);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not equal");
        }
    }

}
