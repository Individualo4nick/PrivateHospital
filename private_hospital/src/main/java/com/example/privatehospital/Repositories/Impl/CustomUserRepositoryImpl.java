package com.example.privatehospital.Repositories.Impl;

import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Repositories.CustomUserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserByIdUser(Long id) {
        List<Record> records = jdbcTemplate.query("SELECT * FROM records WHERE user_id = " + id.toString(), new BeanPropertyRowMapper<>(Record.class));
        for (Record record : records){
            try {
                Staff staff = jdbcTemplate.queryForObject("SELECT * FROM staff WHERE id = (SELECT staff_id from records where user_id=" + id + " LIMIT 1)", new BeanPropertyRowMapper<>(Staff.class));
                record.setStaff(staff);
            }
            catch (EmptyResultDataAccessException exception){
                record.setStaff(new Staff());
            }
        }
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = " + id, new BeanPropertyRowMapper<>(User.class)).setRecords(records);
    }
}
