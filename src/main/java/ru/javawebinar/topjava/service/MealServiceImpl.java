package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Meal> getAll() {
        return null;
    }

    @Override
    public List<Meal> getFiltered(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Meal create(Meal meal) {
        return null;
    }

    @Override
    public void delete(int id) throws NotFoundException {

    }

    @Override
    public void update(Meal meal) throws NotFoundException {

    }

    @Override
    public Meal get(int id) throws NotFoundException {
        return null;
    }
}