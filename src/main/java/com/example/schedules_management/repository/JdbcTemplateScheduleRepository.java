package com.example.schedules_management.repository;

import com.example.schedules_management.dto.ScheduleResponseDto;
import com.example.schedules_management.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("WRITER",schedule.getWriter());
        parameters.put("CONTENTS",schedule.getContents());
        parameters.put("PASSWORD",schedule.getPassword());
        parameters.put("CREATED_DATE",schedule.getCreatedDate());
        parameters.put("REWRITE_DATE",schedule.getRewriteDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getWriter(),schedule.getContents(),schedule.getPassword(),schedule.getCreatedDate(),schedule.getRewriteDate());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return List.of();
    }

    @Override
    public Schedule findScheduleByID(Long id) {
        return null;
    }

    @Override
    public void deleteSchedule(Long id) {

    }
}
