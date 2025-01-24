package com.example.schedules_management.controller;


import com.example.schedules_management.dto.ScheduleRequestDto;
import com.example.schedules_management.dto.ScheduleResponseDto;
import com.example.schedules_management.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //스케쥴 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto responseDto){

        //Service Layer 호출, 응답
        return new ResponseEntity<>(scheduleService.saveSchedule(responseDto), HttpStatus.CREATED);
    }


    //스케쥴 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto>  findScheduleById(@PathVariable Long id){
        return new ResponseEntity<>(scheduleService.findScheduleByID(id),HttpStatus.OK);
    }

    //스케쥴 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
        return new ResponseEntity<>(scheduleService.findAllSchedules(),HttpStatus.OK);
    }

    //스케쥴 단건 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id,requestDto.getContents(),requestDto.getWriter(),requestDto.getPassword()),HttpStatus.OK);
    }

    //스케쥴 단건 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto){
        scheduleService.deleteSchedule(id,requestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
