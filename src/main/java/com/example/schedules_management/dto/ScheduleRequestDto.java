package com.example.schedules_management.dto;

import lombok.Getter;

@Getter

public class ScheduleRequestDto {
    private String contents;
    private String writer;
    private String password;
    private String createdDate;
    private String rewriteDate;
}
