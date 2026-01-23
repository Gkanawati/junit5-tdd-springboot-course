package br.com.gkanawati.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * This class contains unit tests for the {@link SimpleMath} class.
 */
@DisplayName("SimpleMath Unit Tests")
class SimpleMathTestS4 {

  @ParameterizedTest
  @DisplayName("Testing division method: dividing two numbers should return the correct quotient")
  @MethodSource("testDivisionInputParameters")
  // or just @MethodSource() if the method name is the same as the test method name
  void testDivision(double firstNumber, double secondNumber, double expected) {
    // given
    SimpleMath simpleMath = new SimpleMath();

    // when
    double result = simpleMath.division(firstNumber, secondNumber);

    // then
    assertEquals(expected, result, () -> "Division result should be correct");
  }

  public static Stream<Arguments> testDivisionInputParameters() {
    return Stream.of(
        Arguments.of(8D, 2D, 4D),
        Arguments.of(9D, 3D, 3D),
        Arguments.of(7D, 2D, 3.5D),
        Arguments.of(5D, 2D, 2.5D)
    );
  }

}