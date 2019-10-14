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
        //for (Meal meal: MealsUtil.MEALS) {
        this.save(MealsUtil.MEALS.get(0), UsersUtil.USERS.get(0));
        this.save(MealsUtil.MEALS.get(1), UsersUtil.USERS.get(0));
        this.save(MealsUtil.MEALS.get(2), UsersUtil.USERS.get(0));
        this.save(MealsUtil.MEALS.get(3), UsersUtil.USERS.get(0));
        this.save(MealsUtil.MEALS.get(4), UsersUtil.USERS.get(0));
        this.save(MealsUtil.MEALS.get(5), UsersUtil.USERS.get(0));

        this.save(MealsUtil.MEALS.get(6), UsersUtil.USERS.get(1));
        this.save(MealsUtil.MEALS.get(7), UsersUtil.USERS.get(1));
        this.save(MealsUtil.MEALS.get(8), UsersUtil.USERS.get(1));
        this.save(MealsUtil.MEALS.get(9), UsersUtil.USERS.get(2));
        this.save(MealsUtil.MEALS.get(10), UsersUtil.USERS.get(2));
        this.save(MealsUtil.MEALS.get(11), UsersUtil.USERS.get(2));

        //}
    }

    @Override
    public Meal save(Meal meal, User user) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(user.getId());
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
        // create non-null
        Map<Integer, Meal> subRepository = new ConcurrentHashMap<>();;

        for (Map.Entry<Integer, Meal> entry : repository.entrySet()) {
            if (entry.getValue().getUserId() == (int) user.getId()) {
                subRepository.put(entry.getKey(), entry.getValue());
            }
        }
        return subRepository;

    }


}

