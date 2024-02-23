# junit4-test-utils

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
