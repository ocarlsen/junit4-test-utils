package com.ocarlsen.test.util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.OrderWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@OrderWith(OrderAnnotation.class)
public class ExampleTest {

    private static int counter = -2;

    @BeforeClass
    public static void checkCounter_2() {
        assertThat(counter, is(-2));
    }

    @AfterClass
    public static void checkCounter2() {
        assertThat(++counter, is(2));
    }

    @Order(-1)
    @Test
    public void test_1() {
        assertThat(++counter, is(-1));
    }

    @Test
    public void test() {
        assertThat(++counter, is(0));
    }

    @Order(1)
    @Test
    public void test1() {
        assertThat(++counter, is(1));
    }
}
