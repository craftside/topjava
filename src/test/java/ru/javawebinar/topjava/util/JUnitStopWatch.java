package ru.javawebinar.topjava.util;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import ru.javawebinar.topjava.service.MealServiceTest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class JUnitStopWatch extends Stopwatch {
    private static final Logger log = getLogger(JUnitStopWatch.class);

    private static List<String> tests = new LinkedList<String>(Collections.singleton("Method name        time, ms"));

    private static void logInfo(Description description, long nanos) {

        String testName = description.getMethodName();
        long time = TimeUnit.NANOSECONDS.toMillis(nanos);
        String str = String.format("%-15s %6d ms", testName, time);

        tests.add(str);
        log.info("\n" + str);
    }

    public static List<String> getTests() {
        return tests;
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, nanos);
    }
}
