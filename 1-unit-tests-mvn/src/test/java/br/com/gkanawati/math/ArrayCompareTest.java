package br.com.gkanawati.math;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

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

}