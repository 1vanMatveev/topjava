package ru.javawebinar.topjava.service.user;


import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;

import ru.javawebinar.topjava.model.User;


import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(UserTestData.USER_ID);
        UserTestData.assertMatch(user,UserTestData.USER);
        MealTestData.assertMatch(user.getMeals(),MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6);
    }
}
