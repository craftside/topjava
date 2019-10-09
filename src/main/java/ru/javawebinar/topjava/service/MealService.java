package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {

    int addMeal(Meal meal);

    Meal getMeal(int id);

    int updateMeal(int id, List<Meal> mealList);

    void removeMeal (int id);

}
