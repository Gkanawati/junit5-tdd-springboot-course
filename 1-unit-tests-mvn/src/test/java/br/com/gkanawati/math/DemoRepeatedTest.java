package br.com.gkanawati.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

/**
 * This class contains repeated unit tests studies for the {@link SimpleMath} class.
 */
@DisplayName("SimpleMath Unit Tests")
class DemoRepeatedTest {

  SimpleMath simpleMath;

  @BeforeEach
  void init() {
    simpleMath = new SimpleMath();
    System.out.println("\nRunning @BeforeEach init method...");
  }

  @RepeatedTest(value = 5, name = "{displayName} - repetition {currentRepetition} of {totalRepetitions}")
  @DisplayName("Testing division method: dividing by zero should throw ArithmeticException")
  void testDivision_ByZero_ShouldThrowException(
      RepetitionInfo repetitionInfo,
      TestInfo testInfo
  ) {
    System.out.println("Running repetition " + repetitionInfo.getCurrentRepetition() +
        " of " + repetitionInfo.getTotalRepetitions());

    System.out.println("Test method: " + testInfo);
    System.out.println("Test method Name: " + testInfo.getTestMethod().get().getName());

    // given
    double firstNumber = 6.2D;
    double secondNumber = 0D;
    var expectedExceptionMessage = "Impossível dividir por zero";

    // when & then
    ArithmeticException actual = assertThrows(ArithmeticException.class,
        () -> simpleMath.division(firstNumber, secondNumber)
    );

    assertEquals(expectedExceptionMessage, actual.getMessage(),
        () -> "Exception message must be: " + expectedExceptionMessage);
  }

}