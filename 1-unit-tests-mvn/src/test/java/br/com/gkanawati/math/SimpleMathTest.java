package br.com.gkanawati.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the {@link SimpleMath} class.
 */
class SimpleMathTest {

  @Test
  void testSum() {
    SimpleMath simpleMath = new SimpleMath();
    double firstNumber = 6.2D;
    double secondNumber = 2D;

    Double actual = simpleMath.sum(firstNumber, secondNumber);
    double expected = 8.2D;

    assertNotNull(actual);
    // lazily using a lambda expression (`() -> {}`) to build the fail message
    // when assertion fails, the message is built and displayed
    assertEquals(expected, actual, () -> firstNumber + " + " + secondNumber + " must be " + expected);
  }

  @Test
  void testSubtraction() {
    SimpleMath simpleMath = new SimpleMath();
    double firstNumber = 6.2D;
    double secondNumber = 2D;

    Double actual = simpleMath.subtraction(firstNumber, secondNumber);
    double expected = 4.2D;

    assertNotNull(actual);
    assertEquals(expected, actual, () -> firstNumber + " - " + secondNumber + " must be " + expected);
  }

  @Test
  void testMultiplication() {
    SimpleMath simpleMath = new SimpleMath();
    double firstNumber = 6.2D;
    double secondNumber = 2D;

    Double actual = simpleMath.multiplication(firstNumber, secondNumber);
    double expected = 12.4D;

    assertNotNull(actual);
    assertEquals(expected, actual, () -> firstNumber + " * " + secondNumber + " must be " + expected);
  }

  @Test
  void testDivision() {
    SimpleMath simpleMath = new SimpleMath();
    double firstNumber = 6.2D;
    double secondNumber = 2D;

    Double actual = simpleMath.division(firstNumber, secondNumber);
    double expected = 3.1D;

    assertNotNull(actual);
    assertEquals(expected, actual, () -> firstNumber + " / " + secondNumber + " must be " + expected);
  }

  @Test
  void testMean() {
    SimpleMath simpleMath = new SimpleMath();
    double firstNumber = 6.2D;
    double secondNumber = 2D;

    Double actual = simpleMath.mean(firstNumber, secondNumber);
    double expected = 4.1D;

    assertNotNull(actual);
    assertEquals(expected, actual, () -> "Mean of " + firstNumber + " and " + secondNumber + " must be " + expected);
  }

  @Test
  void testSquareRoot() {
    SimpleMath simpleMath = new SimpleMath();
    double number = 81D;

    Double actual = simpleMath.squareRoot(number);
    double expected = 9D;

    assertNotNull(actual);
    assertEquals(expected, actual, () -> "Square root of " + number + " must be " + expected);
  }
}