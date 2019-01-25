package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private final MealService service;
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll(){
        log.info("getAll");
        return service.getAll();
    }

    public List<Meal> getFiltered(String startTime, String endTime, String startDate, String endDate){
        log.info("getFiltered. startTime = {}, endTime = {}, startDate = {}, endDate = {}",startTime,endTime, startDate, endDate);
        LocalTime ltStart = startTime.trim().isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime ltEnd = endTime.trim().isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
        LocalDate ldStart = startDate.trim().isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate ldEnd = endDate.trim().isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate);
        return service.getFiltered(ltStart,ltEnd,ldStart,ldEnd);
    }

    public Meal get(int id){
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal create(Meal meal){
        log.info("create {}",meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id){
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal, int id){
        log.info("update {} with id = {}", meal, id);
        assureIdConsistent(meal,id);
        service.update(meal);
    }


}