package com.ocarlsen.test.util;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Ordering;
import org.junit.runner.manipulation.Sorter;

import java.util.Comparator;
import java.util.Optional;

/**
 * Order test methods by the {@link Order} annotation.
 * The lower value has the higher priority.
 * Tests that are not annotated get the default value {@link Order#DEFAULT}.
 */
public class OrderAnnotation extends Sorter implements Ordering.Factory {
    public OrderAnnotation() {
        super(COMPARATOR);
    }

    @Override
    public Ordering create(Context context) {
        return this;
    }

    private static final Comparator<Description> COMPARATOR = Comparator.comparingInt(
            description -> Optional.ofNullable(description.getAnnotation(Order.class))
                                   .map(Order::value)
                                   .orElse(Order.DEFAULT));
}