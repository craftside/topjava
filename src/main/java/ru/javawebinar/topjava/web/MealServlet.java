package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
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

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MealsUtil.init();

        List<MealTo> mealsTo = MealsUtil.getMealsTo();

        DateTimeFormatter formatter = DateTimeFormatterUtil.getFormatter();

        log.debug("set mealsTo to request attribute");
        request.setAttribute("mealsTo", mealsTo);

        log.debug("set formatter to request attribute");
        request.setAttribute("formatter", formatter);

        log.debug("forward to meals jsp");
        request.getRequestDispatcher("meals.jsp").forward(request, response);

//        response.sendRedirect("meals.jsp");
    }
}
