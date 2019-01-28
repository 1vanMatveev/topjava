package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertMeal;
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()){
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("dateTime", meal.getDateTime())
                    .addValue("description",meal.getDescription())
                    .addValue("calories",meal.getCalories())
                    .addValue("user_id", userId);
            meal.setId(insertMeal.executeAndReturnKey(map).intValue());
        } else if(jdbcTemplate.update("update meals set datetime = ?, description = ?, calories = ? where id = ? and user_id = ?",
                    meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId(), userId) == 0){
            return null;
        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE from meals where id = ? and user_id = ?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("select id, dateTime, calories, description from meals where id = ? and user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("select id, dateTime, calories, description from meals " +
                "                       where user_id = ? order by datetime desc", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("select id, dateTime, calories, description from meals" +
                "                       where user_id = ? and  datetime >= ? and datetime <= ? order by datetime desc",
                                        ROW_MAPPER, userId, startDate, endDate);
    }
}
