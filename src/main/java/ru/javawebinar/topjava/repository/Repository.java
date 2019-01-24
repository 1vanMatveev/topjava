package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Repository {

    List<Meal> getAll();
    void remove(int id);
    void save(Meal meal);
    Meal get(int id);
}
