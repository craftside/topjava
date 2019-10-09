package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.impl.MealServiceImpl;
import ru.javawebinar.topjava.util.DateTimeFormatterUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealService mealService = new MealServiceImpl();
    private List<MealTo> mealsTo;

//    public MealServlet() {
//        super();
//        MealService mealService =
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meal meal = new Meal(
                LocalDateTime.of(
                        Integer.valueOf(req.getParameter("year")),
                        Integer.valueOf(req.getParameter("month")),
                        Integer.valueOf(req.getParameter("day")),
                        Integer.valueOf(req.getParameter("hour")),
                        Integer.valueOf(req.getParameter("min"))
                ),
                req.getParameter("desc"),
                Integer.valueOf(req.getParameter("calories"))
                );

        MealService mealService = new MealServiceImpl();
        mealService.addMeal(meal);

        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");

        DateTimeFormatter formatter = DateTimeFormatterUtil.getFormatter();

        if (action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            mealService.removeMeal(id);
            forward = "/listOfMeals.jsp";

            mealsTo = MealsUtil.getMealsTo();
            request.removeAttribute("action");
            request.setAttribute("formatter", formatter);
            request.setAttribute("mealsTo", mealsTo);

        } else if (action.equalsIgnoreCase("edit")){
            forward = "meal.jsp";
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealService.getMeal(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listofmeals")){
            forward = "listOfMeals.jsp";

            mealsTo = MealsUtil.getMealsTo();
            request.setAttribute("mealsTo", mealsTo);
            request.setAttribute("formatter", formatter);

        } else {
            request.setAttribute("formatter", formatter);
            forward = "listOfMeals.jsp";
        }

        request.getRequestDispatcher(forward).forward(request, response);


//        List<MealTo> mealsTo = MealsUtil.getMealsTo();
//
//
//
//        DateTimeFormatter formatter = DateTimeFormatterUtil.getFormatter();
//
//        log.debug("set mealsTo to request attribute");
//        request.setAttribute("mealsTo", mealsTo);
//
//        log.debug("set formatter to request attribute");
//        request.setAttribute("formatter", formatter);
//
//        log.debug("forward to meals jsp");
//        request.getRequestDispatcher("listOfMeals.jsp").forward(request, response);

//        response.sendRedirect("listOfMeals.jsp");
    }
}
