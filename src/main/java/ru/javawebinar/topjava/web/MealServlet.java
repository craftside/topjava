package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DateTimeFormatterUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MealServlet extends HttpServlet {

    private static final int DEFAULT_CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            MealsUtil.init();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<MealTo> mealsTo = MealsUtil.getMealsTo();

        DateTimeFormatter formatter = DateTimeFormatterUtil.getFormatter();

        request.setAttribute("mealsTo", mealsTo);
        request.setAttribute("formatter", formatter);
        request.getRequestDispatcher("meals.jsp").forward(request, response);

//        response.sendRedirect("meals.jsp");
    }
}
