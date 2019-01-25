package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    List<Meal> getAll(int userId);
    List<Meal> getFiltered(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, int userId);
    Meal create(Meal meal, int userId);
    void delete(int id, int userId) throws NotFoundException;
    void update(Meal meal, int userId) throws NotFoundException;
    Meal get(int id, int userId) throws NotFoundException;



}