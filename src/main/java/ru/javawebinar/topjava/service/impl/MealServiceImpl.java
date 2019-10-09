package ru.javawebinar.topjava.service.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealServiceImpl implements MealService {

    private int id;

    private static List<Meal> db = new ArrayList<>(Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));

    public static List<Meal> getDb() {
        return db;
    }

    public static void setDb(List<Meal> db) {
        MealServiceImpl.db = db;
    }

    @Override
    public int addMeal(Meal meal) {


        db.add(meal);

        return 0;
    }

    @Override
    public Meal getMeal(int id) {

        return db.get(id);

    }

    @Override
    public int updateMeal(int id, List<Meal> mealList) {
        return 0;
    }

    @Override
    public void removeMeal(int id) {

        for(int i=0; i < db.size(); i++){
            Meal meal = db.get(i);
            if(meal.getId() == id)
                db.remove(db.get(i));
        }

    }
}
