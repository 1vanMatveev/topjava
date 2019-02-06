package ru.javawebinar.topjava.repository.jdbc.meal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;

public class PostgresMealRepositoryImpl extends AbstractMealRepositoryImpl<LocalDateTime> {

    public PostgresMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected LocalDateTime convToDbDate(LocalDateTime ldt) {
        return ldt;
    }
}
