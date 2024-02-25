# junit4-test-utils

[//]: # ([![Maven Central]&#40;https://img.shields.io/maven-central/v/com.ocarlsen.test/junit4-test-utils.svg?label=Maven%20Central&#41;]&#40;https://central.sonatype.com/artifact/com.ocarlsen.test/junit4-test-utils&#41;)
[![Build](https://github.com/ocarlsen/junit4-test-utils/actions/workflows/build.yml/badge.svg)](https://github.com/ocarlsen/junit4-test-utils/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_junit4-test-utils&metric=alert_status)](https://sonarcloud.io/dashboard?id=ocarlsen_junit4-test-utils)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_junit4-test-utils&metric=coverage)](https://sonarcloud.io/dashboard?id=ocarlsen_junit4-test-utils)

Utilities for testing with JUnit 4.

## Dependency Information

### Maven

    <dependency>
        <groupId>com.ocarlsen.test</groupId>
        <artifactId>junit4-test-utils</artifactId>
        <version>1.0</version>
        <scope>test</scope>
    </dependency>

### Gradle

    compile 'com.ocarlsen.test:junit4-test-utils:1.0'

## Example Code

### Order

Use the `com.ocarlsen.test.util.Order` annotation in JUnit 4 (>= 4.13)
like you would the `org.junit.jupiter.api.Order` annotation in JUnit 5.

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

This example is demonstrated in
[ExampleTest](https://github.com/ocarlsen/junit4-test-utils/blob/main/src/test/java/com/ocarlsen/test/util/ExampleTest.java).

