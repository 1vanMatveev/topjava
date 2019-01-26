package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.checkUserId;


@Controller
public class MealRestController {

    private final MealService service;
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll(){
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(SecurityUtil.getAuthUserId()),SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(String startTime, String endTime, String startDate, String endDate){
        log.info("getFiltered. startTime = {}, endTime = {}, startDate = {}, endDate = {}",startTime,endTime, startDate, endDate);
        LocalTime ltStart = startTime.trim().isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime ltEnd = endTime.trim().isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
        LocalDate ldStart = startDate.trim().isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate ldEnd = endDate.trim().isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate);
        return MealsUtil.getWithExceeded(service.getFiltered(ltStart,ltEnd,ldStart,ldEnd, SecurityUtil.getAuthUserId()),SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id){
        log.info("get {}", id);
        return service.get(id, SecurityUtil.getAuthUserId());
    }

    public Meal create(Meal meal){
        log.info("create {}",meal);
        checkNew(meal);
        checkUserId(meal,SecurityUtil.getAuthUserId());
        return service.create(meal, SecurityUtil.getAuthUserId());
    }

    public void delete(int id){
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.getAuthUserId());
    }

    public void update(Meal meal, int id){
        log.info("update {} with id = {}", meal, id);
        assureIdConsistent(meal,id);
        checkUserId(meal,SecurityUtil.getAuthUserId());
        service.update(meal, SecurityUtil.getAuthUserId());
    }




}