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
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get() {
        Meal actual = mealService.get(MealTestData.MEAL_LIST.get(0).getId(), SecurityUtil.authUserId());
        MealTestData.assertMatch(actual, MealTestData.MEAL_LIST.get(0));
    }

    @Test
    public void delete() {
        mealService.delete(MealTestData.MEAL_LIST.get(0).getId(), SecurityUtil.authUserId());
        MealTestData.assertMatch(mealService.getAll(SecurityUtil.authUserId()),
                MealTestData.MEAL_LIST.get(5),
                MealTestData.MEAL_LIST.get(4),
                MealTestData.MEAL_LIST.get(3),
                MealTestData.MEAL_LIST.get(2),
                MealTestData.MEAL_LIST.get(1));
    }

    @Test
    public void getBetweenDates() {
        List<Meal> actual = mealService.getBetweenDates(LocalDate.of(2015, 05, 30), LocalDate.of(2015, 05, 30), SecurityUtil.authUserId());
        MealTestData.assertMatch(actual,
                MealTestData.MEAL_LIST.get(2),
                MealTestData.MEAL_LIST.get(1),
                MealTestData.MEAL_LIST.get(0));

    }

    @Test
    public void getAll() {
        List<Meal> mealList = mealService.getAll(SecurityUtil.authUserId());
        MealTestData.assertMatch(mealList,
                MealTestData.MEAL_LIST.get(5),
                MealTestData.MEAL_LIST.get(4),
                MealTestData.MEAL_LIST.get(3),
                MealTestData.MEAL_LIST.get(2),
                MealTestData.MEAL_LIST.get(1),
                MealTestData.MEAL_LIST.get(0));
    }

    @Test
    public void update() {
        Meal updated = new Meal(MealTestData.MEAL_LIST.get(0));
        updated.setDescription("TestUpdate");
        updated.setCalories(9999);
        updated.setDateTime(LocalDateTime.now());
        mealService.update(updated, SecurityUtil.authUserId());
        MealTestData.assertMatch(mealService.get(MealTestData.MEAL_LIST.get(0).getId(), SecurityUtil.authUserId()),
                updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2019, 10, 19, 21, 26, 00), "Test Food", 1299);
        int userId = SecurityUtil.authUserId();
        Meal created = mealService.create(newMeal, userId);
        MealTestData.assertMatch(mealService.getAll(userId),
                created,
                MealTestData.MEAL_LIST.get(5),
                MealTestData.MEAL_LIST.get(4),
                MealTestData.MEAL_LIST.get(3),
                MealTestData.MEAL_LIST.get(2),
                MealTestData.MEAL_LIST.get(1),
                MealTestData.MEAL_LIST.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(MealTestData.MEAL_LIST.get(7).getId(), SecurityUtil.authUserId());
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        mealService.get(MealTestData.MEAL_LIST.get(7).getId(), SecurityUtil.authUserId());
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = new Meal(MealTestData.MEAL_LIST.get(7));
        updated.setDescription("TestUpdate2");
        updated.setCalories(1233);
        updated.setDateTime(LocalDateTime.now());
        mealService.update(updated, SecurityUtil.authUserId());
        MealTestData.assertMatch(mealService.get(MealTestData.MEAL_LIST.get(0).getId(), SecurityUtil.authUserId()),
                updated);
    }
}