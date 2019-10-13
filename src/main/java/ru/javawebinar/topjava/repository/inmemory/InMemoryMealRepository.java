package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal: MealsUtil.MEALS) {
            this.save(meal, UsersUtil.USERS.get(1));

        }
    }

    @Override
    public Meal save(Meal meal, User user) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return getMealByUser(user).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, User user) {
        return (getMealByUser(user).remove(id) != null & repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, User user) {
        return getMealByUser(user).get(id);
    }

    @Override
    public Collection<Meal> getAll(User user) {
        return getMealByUser(user).values();
    }

    @Override
    public Map<Integer, Meal> getMealByUser(User user) {
        Map<Integer, Meal> subRepository = null;

        for (Map.Entry<Integer, Meal> entry : repository.entrySet()) {
            if (entry.getValue().getUserId() == user.getId()) {
                subRepository.put(entry.getKey(), entry.getValue());
            }
        }
        return subRepository;

    }


}

