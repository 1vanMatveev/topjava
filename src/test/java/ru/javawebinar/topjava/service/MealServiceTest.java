package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2018, Month.DECEMBER, 25,10,10),"Перекус", 300);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertThat(service.getAll(USER_ID)).isEqualTo(Arrays.asList(USER_MEAL4,USER_MEAL3, USER_MEAL2, USER_MEAL1, created));
    }

    @Test
    public void delete() {
        service.delete(FIRST_MEAL_ID,USER_ID);
        assertThat(service.getAll(USER_ID)).isEqualTo(Arrays.asList(USER_MEAL4,USER_MEAL3, USER_MEAL2));
    }

    @Test
    public void update() {
        Meal meal = new Meal(USER_MEAL1);
        meal.setCalories(1300);
        meal.setDescription("Полдник");
        service.update(meal,USER_ID);
        assertThat(service.get(FIRST_MEAL_ID,USER_ID)).isEqualTo(meal);
    }

    @Test
    public void get(){
        assertThat(service.get(FIRST_MEAL_ID+1,USER_ID)).isEqualTo(USER_MEAL2);
    }

    @Test
    public void getAll() {
        assertThat(service.getAll(USER_ID)).isEqualTo(Arrays.asList(USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1));
    }

    @Test
    public void getBetweenDates(){
        LocalDate startDate = LocalDate.of(2019,Month.JANUARY,2);
        LocalDate endDate = LocalDate.of(2019,Month.JANUARY,2);
        assertThat(service.getBetweenDates(startDate,endDate,USER_ID))
                .isEqualTo(Arrays.asList(USER_MEAL2,USER_MEAL1));
    }

    @Test
    public void getBetweenDateTimes(){
        LocalDateTime startDT = LocalDateTime.of(2019,Month.JANUARY,2,13,0);
        LocalDateTime endDT = LocalDateTime.of(2019,Month.JANUARY,3,11,0);
        assertThat(service.getBetweenDateTimes(startDT,endDT,USER_ID))
                .isEqualTo(Arrays.asList(USER_MEAL3,USER_MEAL2));

    }



    @Test(expected = NotFoundException.class)
    public void deleteForeignMeal() {
        service.delete(FIRST_MEAL_ID +2, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getForeignMeal(){
        service.get(FIRST_MEAL_ID + 4, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeignMeal(){
        service.update(ADMIN_MEAL1, USER_ID);
    }




}