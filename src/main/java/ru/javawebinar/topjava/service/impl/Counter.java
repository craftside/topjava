package ru.javawebinar.topjava.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    static AtomicInteger id = new AtomicInteger(0);

    public static int getId() {
        return id.incrementAndGet();
    }

}
