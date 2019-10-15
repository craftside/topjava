package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS =Arrays.asList(
            new User(1,
                    "Admin",
                    "admin@mail.ru",
                    "123",
                    MealsUtil.DEFAULT_CALORIES_PER_DAY,
                    true,
                    Collections.singleton(Role.ROLE_ADMIN)),
            new User(2,
                    "User1",
                    "first@mail.ru",
                    "456",
                    MealsUtil.DEFAULT_CALORIES_PER_DAY,
                    true,
                    Collections.singleton(Role.ROLE_USER)),
            new User(3,
                    "User2",
                    "second@mail.ru",
                    "789",
                    MealsUtil.DEFAULT_CALORIES_PER_DAY,
                    true,
                    Collections.singleton(Role.ROLE_USER))

    );
}
