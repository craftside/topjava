package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
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


@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<Integer, Meal>();
    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        for (int i = 0; i <=8; i++) {
            save(MealsUtil.MEALS.get(i), 1);
        }

        for (int i = 9; i <=11; i++) {
            save(MealsUtil.MEALS.get(i), 2);
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            log.info("save {}", meal);
            return meal;

        }
        // if user has that food so than we update it
        if (getMealByUser(userId).containsKey(meal.getId())) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
//        get(id, userId);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return getMealByUser(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getMealByUser(userId).values();
    }

    @Override
    public Map<Integer, Meal> getMealByUser(int userId) {
        // create non-null
        Map<Integer, Meal> subRepository = new ConcurrentHashMap<>();

        for (Map.Entry<Integer, Meal> entry : repository.entrySet()) {
            if (entry.getValue().getUserId() == userId) {
                subRepository.put(entry.getKey(), entry.getValue());
            }
        }
        return subRepository;

    }


}

