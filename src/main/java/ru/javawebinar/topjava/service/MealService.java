package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    List<Meal> getAll();
    List<Meal> getFiltered(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate);
    Meal create(Meal meal);
    void delete(int id) throws NotFoundException;
    void update(Meal meal) throws NotFoundException;
    Meal get(int id) throws NotFoundException;



}