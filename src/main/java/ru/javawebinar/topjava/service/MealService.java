package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;

@Service
public class MealService {

    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal create(Meal meal, int userId) {
        return mealRepository.save(meal, userId);
    }

    public void delete(int mealId, int userId) {
        mealRepository.delete(mealId, userId);
    }

    public Meal get(int mealId, int userId) {
        return mealRepository.get(mealId, userId);
    }

    public Collection<Meal> getAll(int userId) {
        return mealRepository.getAll(userId);
    }

    public  void update(Meal meal, int userId) {
        mealRepository.save(meal, userId);
    }


}