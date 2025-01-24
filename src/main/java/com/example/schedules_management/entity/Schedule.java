package com.example.schedules_management.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String writer;
    private String contents;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime rewriteDate;

    public Schedule(String writer, String contents, String password){
        this.writer = writer;
        this.contents = contents;
        this.password = password;
        this.createdDate = LocalDateTime.now();
        this.rewriteDate = LocalDateTime.now();
    }

    public void update(String contents, String writer) {
        this.contents = contents;
        this.writer = writer;
        this.rewriteDate = LocalDateTime.now();
    }
}
