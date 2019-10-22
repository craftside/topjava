package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app-common.xml",
        "classpath:spring/spring-app-jdbc-impl.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal actual = mealService.get(MealTestData.MEAL_0.getId(), UserTestData.USER_ID);
        MealTestData.assertMatch(actual, MealTestData.MEAL_0);
    }

    @Test
    public void delete() {
        mealService.delete(MealTestData.MEAL_0.getId(), UserTestData.USER_ID);
        MealTestData.assertMatch(mealService.getAll(UserTestData.USER_ID),
                MealTestData.MEAL_5,
                MealTestData.MEAL_4,
                MealTestData.MEAL_3,
                MealTestData.MEAL_2,
                MealTestData.MEAL_1
        );
    }

    @Test
    public void getBetweenDates() {
        List<Meal> actual = mealService.getBetweenDates(LocalDate.of(2015, 5, 30), LocalDate.of(2015, 5, 30), UserTestData.USER_ID);
        MealTestData.assertMatch(actual,
                MealTestData.MEAL_2,
                MealTestData.MEAL_1,
                MealTestData.MEAL_0
        );

    }

    @Test
    public void getAll() {
        List<Meal> mealList = mealService.getAll(UserTestData.USER_ID);
        MealTestData.assertMatch(mealList,
                MealTestData.MEAL_5,
                MealTestData.MEAL_4,
                MealTestData.MEAL_3,
                MealTestData.MEAL_2,
                MealTestData.MEAL_1,
                MealTestData.MEAL_0
        );
    }

    @Test
    public void update() {
        Meal updated = new Meal(MealTestData.MEAL_0);
        updated.setDescription("TestUpdate");
        updated.setCalories(9999);
        updated.setDateTime(LocalDateTime.now());
        mealService.update(updated, UserTestData.USER_ID);
        MealTestData.assertMatch(mealService.get(MealTestData.MEAL_0.getId(), UserTestData.USER_ID),
                updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2019, 10, 19, 21, 26, 00), "Test Food", 1299);
        Meal created = mealService.create(newMeal, UserTestData.USER_ID);
        MealTestData.assertMatch(mealService.getAll(UserTestData.USER_ID),
                created,
                MealTestData.MEAL_5,
                MealTestData.MEAL_4,
                MealTestData.MEAL_3,
                MealTestData.MEAL_2,
                MealTestData.MEAL_1,
                MealTestData.MEAL_0
        );
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(MealTestData.MEAL_7.getId(), UserTestData.USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        mealService.get(MealTestData.MEAL_7.getId(), UserTestData.USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = new Meal(MealTestData.MEAL_7);
        updated.setDescription("TestUpdate2");
        updated.setCalories(1233);
        updated.setDateTime(LocalDateTime.now());
        mealService.update(updated, UserTestData.USER_ID);
        MealTestData.assertMatch(mealService.get(MealTestData.MEAL_7.getId(), UserTestData.USER_ID),
                updated);
    }

    @Test(expected = org.springframework.dao.DuplicateKeyException.class)
    public void createDublicate() {
        Meal dublicate = new Meal(MealTestData.MEAL_0);
        dublicate.setId(null);
        mealService.create(dublicate, UserTestData.USER_ID);
    }
}