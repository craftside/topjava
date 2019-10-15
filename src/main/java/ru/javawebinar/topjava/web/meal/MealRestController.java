package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.UsersUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {

    @Autowired
    private MealService mealService;

    @Autowired
    private UserService userService;

    public void delete(int id) {
        mealService.delete(id, SecurityUtil.authUserId());
    }

    public Meal create() {

        return null;
    }

    public Meal get(int id) {

        return null;
    }

    public Collection<Meal> getAll() {

        return mealService.getAll(SecurityUtil.authUserId());
    }

    public void save(Meal meal) {

    }
}