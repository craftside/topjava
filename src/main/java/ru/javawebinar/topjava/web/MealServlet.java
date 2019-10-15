package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UsersUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            mealRestController = appCtx.getBean(MealRestController.class);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
//        Integer userId = SecurityUtil.authUserId();
//        User user = profileRestController.get();

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        //mealRepository.save(meal, userRepository.get(user.getId()));
        mealRestController.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = Arrays.asList(UsersUtil.USERS.get(0), UsersUtil.USERS.get(1));
        request.setAttribute("users", users);


        String userParameter = request.getParameter("user");
        if (userParameter != null) {
            SecurityUtil.setUserId( Integer.valueOf(userParameter));
        }
        request.setAttribute("authUser", SecurityUtil.authUserId());

        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
//                mealRepository.delete(id, userRepository.get(user.getId()));
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
//                final Meal meal = "create".equals(action) ?
//                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, user.getId()) :
//                        mealRepository.get(getId(request), userRepository.get(user.getId()));
                final Meal meal = "create".equals(action) ?
                        mealRestController.create() :
                        mealRestController.get(getId(request));


                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
//                request.setAttribute("meals",
//                        MealsUtil.getTos(mealRepository.getAll(userRepository.get(user.getId())), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                Collection<Meal> collectionMeal = mealRestController.getAll();
                request.setAttribute("meals",
                          MealsUtil.getTos(collectionMeal, MealsUtil.DEFAULT_CALORIES_PER_DAY));

                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}