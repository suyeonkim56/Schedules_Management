package com.example.schedules_management.repository;

import com.example.schedules_management.dto.ScheduleResponseDto;
import com.example.schedules_management.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        return jdbcTemplate.query("select * from schedule order by REWRITE_DATE desc ",scheduleRowMapper());
    }

    @Override
    public Optional<Schedule> findScheduleByID(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public int updateSchedule(Long id, String writer, String contents, String password) {
        return jdbcTemplate.update("update schedule set WRITER = ?, CONTENTS = ?, REWRITE_DATE = ? where ID = ? AND PASSWORD = ?", writer, contents, LocalDateTime.now(), id, password);
    }

    @Override
    public int deleteSchedule(Long id, String password) {
        return jdbcTemplate.update("delete from schedule where ID = ? AND PASSWORD = ?",id,password);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("ID"),
                        rs.getString("WRITER"),
                        rs.getString("CONTENTS"),
                        rs.getString("PASSWORD"),
                        rs.getTimestamp("CREATED_DATE").toLocalDateTime(),
                        rs.getTimestamp("REWRITE_DATE").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2(){
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(rs.getLong("ID"),
                        rs.getString("WRITER"),
                        rs.getString("CONTENTS"),
                        rs.getString("PASSWORD"),
                        rs.getTimestamp("CREATED_DATE").toLocalDateTime(),
                        rs.getTimestamp("REWRITE_DATE").toLocalDateTime());
            }
        };
    }
}
