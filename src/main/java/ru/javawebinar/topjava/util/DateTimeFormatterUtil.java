package ru.javawebinar.topjava.util;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatterUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }
}
