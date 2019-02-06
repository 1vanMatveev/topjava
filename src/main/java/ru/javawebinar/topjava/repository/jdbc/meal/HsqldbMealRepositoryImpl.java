package ru.javawebinar.topjava.repository.jdbc.meal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HsqldbMealRepositoryImpl extends AbstractMealRepositoryImpl<Timestamp> {

    public HsqldbMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp convToDbDate(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }
}
