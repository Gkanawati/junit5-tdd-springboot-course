package br.com.gkanawati.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the {@link SimpleMath} class.
 */
@DisplayName("SimpleMath Unit Tests")
class SimpleMathTest {

  // conventional naming:
  // test[MethodName]_[StateUnderTest]_[ExpectedBehavior]

  // Ways to organize tests:
  // BDD -> Given-When-Then
  // or
  // AAA -> Arrange-Act-Assert

  @Test
  @DisplayName("Testing sum method: adding two numbers should return the correct sum")
  void testSum_WhenAddingTwoNumbers_ThenReturnsCorrectSum() {
    // given
    SimpleMath simpleMath = new SimpleMath();
    double firstNumber = 6.2D;
    double secondNumber = 2D;
    double expected = 8.2D;

    // when
    Double actual = simpleMath.sum(firstNumber, secondNumber);

    // then
    assertNotNull(actual);
    // lazily using a lambda expression (`() -> {}`) to build the fail message
    // when assertion fails, the message is built and displayed
    assertEquals(expected, actual, () -> firstNumber + " + " + secondNumber + " must be " + expected);
  }

  @Test
  @DisplayName("Testing division method: dividing by zero should throw ArithmeticException")
  void testDivision_ByZero_ShouldThrowException() {
    SimpleMath simpleMath = new SimpleMath();
    double firstNumber = 6.2D;
    double secondNumber = 0D;

    try {
      simpleMath.division(firstNumber, secondNumber);
    } catch (ArithmeticException ex) {
      assertEquals("/ by zero", ex.getMessage());
    }
  }

  @Test
  @DisplayName("Testing subtraction method: subtracting two numbers should return the correct difference")
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
  @DisplayName("Testing multiplication method: multiplying two numbers should return the correct product")
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
  @DisplayName("Testing division method: dividing two numbers should return the correct quotient")
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
  @DisplayName("Testing mean method: calculating the mean of two numbers should return the correct average")
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
  @DisplayName("Testing squareRoot method: calculating the square root of a number should return the correct result")
  void testSquareRoot() {
    SimpleMath simpleMath = new SimpleMath();
    double number = 81D;

    Double actual = simpleMath.squareRoot(number);
    double expected = 9D;

    assertNotNull(actual);
    assertEquals(expected, actual, () -> "Square root of " + number + " must be " + expected);
  }

  @Disabled
  @Test
  @DisplayName("Disabled test example")
  void disabledTestExample() {
    // This test is disabled and will not run
  }
}