package com.ocarlsen.test.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JUnit 4 equivalent of JUnit 5's {@code org.junit.jupiter.api.Order}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Order {

    /**
     * Default order value for elements not explicitly annotated with {@code @Order}.
     *
     * @see Order#value
     */
    int DEFAULT = 0;

    /**
     * The order value for the annotated element.
     * <p>Tests are ordered based on this value,
     * where a lower value will run before a test with a higher value.
     * For example, {@link Integer#MIN_VALUE} has the highest priority.</p>
     *
     * @see #DEFAULT
     */
    int value();
}

