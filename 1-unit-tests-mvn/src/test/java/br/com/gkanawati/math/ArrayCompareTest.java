package br.com.gkanawati.math;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * This class contains unit tests for the {@link SimpleMath} class.
 */
class ArrayCompareTest {

  @Test
  void testArrayCompare() {
    int[] numbers = {25, 8, 21, 32, 3};
    int[] expected = {3, 8, 21, 25, 32};

    Arrays.sort(numbers);

    assertArrayEquals(numbers, expected);
  }

  // for unit tests, prefer timeouts of milliseconds
  // for integration tests, prefer timeouts of seconds
  @Test
  @Timeout(value = 15, unit = TimeUnit.MILLISECONDS)
  void testSortPerformance() {
    int[] numbers = {25, 8, 21, 32, 3};

    for (int i = 0; i < 10000000; i++) {
      numbers[0] = i;
      Arrays.sort(numbers);
    }

  }

}