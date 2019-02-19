package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService service;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL+MEAL1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(TestUtil.contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception{
        mockMvc.perform(delete(REST_URL+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void testGetAll() throws Exception{
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(TestUtil.contentJson(MEALS));
    }

    @Test
    void testCreate() throws Exception{
        Meal expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Meal returned = readFromJson(action,Meal.class);
        expected.setId(returned.getId());
        assertMatch(returned,expected);
        assertMatch(service.getAll(USER_ID), expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    }

    @Test
    void testUpdate() throws Exception{
        Meal updated = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isNoContent());
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);

    }

    @Test
    void testGetBetween() throws Exception {
        String startDate = "2015-05-30T10:15:30";
        String endDate = "2015-05-30T10:30:30";
        String startTime = "2011-12-03T10:15:30";
        String endTime = "2011-12-03T15:15:30";
        mockMvc.perform(get(REST_URL + "filter?startDate=" + startDate + "&startTime=" + startTime + "&endDate=" + endDate + "&endTime=" + endTime))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(TestUtil.contentJson(List.of(MEAL2)));

    }
}