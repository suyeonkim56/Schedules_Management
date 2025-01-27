package com.example.schedules_management.dto;

import com.example.schedules_management.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String writer;
    private String contents;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime rewriteDate;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.writer = schedule.getWriter();
        this.contents = schedule.getContents();
        this.password = schedule.getPassword();
        this.createdDate = schedule.getCreatedDate();
        this.rewriteDate = schedule.getRewriteDate();
    }
}
