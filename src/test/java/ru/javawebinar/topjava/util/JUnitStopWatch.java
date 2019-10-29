package ru.javawebinar.topjava.util;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JUnitStopWatch extends Stopwatch {
    private static List<String> tests = new LinkedList<>();
    private static long totalTime = 0;

    private static void logInfo(Description description, String status, long nanos) {

        String testName = description.getMethodName();
        long time = TimeUnit.NANOSECONDS.toMillis(nanos);
        String str = String.format("Test %s %s, spent %d ms", testName, status, time);
        tests.add(str);
        totalTime += time;

        System.out.println("\n");
        System.out.println(str);
        System.out.println("\n");


    }

    public static List<String> getTests() {
        return tests;
    }

    public static long getTotalTime() {
        return totalTime;
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }

//    @Override
//    protected void finished(long nanos, Description description) {
//        logInfo(description, "finished", nanos);
//    }
}
