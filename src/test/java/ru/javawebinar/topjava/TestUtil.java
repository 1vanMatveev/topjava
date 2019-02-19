package ru.javawebinar.topjava;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TestUtil {

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action), clazz);
    }

    public static Matcher<List<MealTo>> countExcess(int count){

        return new Matcher<>() {
            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(Object o) {
                List<MealTo> meals = (List<MealTo>)o;
                return meals.stream().filter(MealTo::isExcess).count() == count;
            }
            @SuppressWarnings("unchecked")
            @Override
            public void describeMismatch(Object o, Description description) {
                List<MealTo> meals = (List<MealTo>)o;
                meals.forEach(mealTo -> description.appendText(mealTo.toString() + "\n"));
            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Count meals with excess = " + count);
            }
        };
    }
}
