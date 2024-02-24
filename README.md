# junit4-test-utils

[//]: # ([![Maven Central]&#40;https://img.shields.io/maven-central/v/com.ocarlsen.test/junit4-test-utils.svg?label=Maven%20Central&#41;]&#40;https://central.sonatype.com/artifact/com.ocarlsen.test/junit4-test-utils&#41;)
[![Build](https://github.com/ocarlsen/junit4-test-utils/actions/workflows/build.yml/badge.svg)](https://github.com/ocarlsen/junit4-test-utils/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_junit4-test-utils&metric=alert_status)](https://sonarcloud.io/dashboard?id=ocarlsen_junit4-test-utils)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_junit4-test-utils&metric=coverage)](https://sonarcloud.io/dashboard?id=ocarlsen_junit4-test-utils)


Utilities for testing with JUnit 4.

## Order

Use the `com.ocarlsen.test.util.Order` annotation in JUnit 4 (>=4.13)
like you would the `org.junit.jupiter.api.Order` annotation in JUnit 5.

    @OrderWith(OrderAnnotation.class)
    public class OrderTest {
    
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
