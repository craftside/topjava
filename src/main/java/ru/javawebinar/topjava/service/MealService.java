package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;


    public Collection<Meal> getAll(int userId) {

        return mealRepository.getAll(userRepository.get(userId));

    }


    public void delete(int mealId, int userId) {

        mealRepository.delete(mealId, userRepository.get(userId));
    }
}