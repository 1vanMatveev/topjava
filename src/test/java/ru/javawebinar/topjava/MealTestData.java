package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int FIRST_MEAL_ID = START_SEQ +2;

    public static final List<Meal> USER_MEALS = Arrays.asList(
            new Meal(FIRST_MEAL_ID,LocalDateTime.of(2019, Month.JANUARY, 2, 10, 0), "Завтрак", 500),
            new Meal(FIRST_MEAL_ID + 1,LocalDateTime.of(2019, Month.JANUARY, 2, 14, 0), "Обед", 1000),
            new Meal(FIRST_MEAL_ID + 3,LocalDateTime.of(2019, Month.JANUARY, 3, 10, 0), "Завтрак", 1000),
            new Meal(FIRST_MEAL_ID + 5,LocalDateTime.of(2019, Month.JANUARY, 3, 18, 0), "Ужин", 510)
    );

}
