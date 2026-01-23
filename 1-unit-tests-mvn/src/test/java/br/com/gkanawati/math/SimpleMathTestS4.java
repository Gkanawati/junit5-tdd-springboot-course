package br.com.gkanawati.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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

  @DisplayName("Testing division method with CSV file source [firstNumber, secondNumber, expected]")
  @ParameterizedTest
  @CsvFileSource(resources = "/test-division.csv", numLinesToSkip = 1)
  void testDivisionWithFileSource(double firstNumber, double secondNumber, double expected) {
    // given
    SimpleMath simpleMath = new SimpleMath();

    // when
    double result = simpleMath.division(firstNumber, secondNumber);

    // then
    assertEquals(expected, result, () -> "Division result should be correct");
  }

  @DisplayName("Testing bill calculations with CSV source and multiple parameters")
  @ParameterizedTest
  // using delimiters and headers in display name for better readability
  @CsvSource(delimiter = '|', useHeadersInDisplayName = true, textBlock = """
      Fixture                               | Bill    | Payment | Discount | Interest | Penalty | Allowance
      bill_info                             | 290.81  | 290.81  | 0.00     | 0.00     | 0.00    | 0.00
      bill_info_with_discount               | 100.00  | 90.00   | 10.00    | 0.00     | 0.00    | 0.00
      bill_info_with_allowance              | 50.00   | 40.87   | 0.00     | 0.00     | 0.00    | 9.13
      bill_info_with_discount_and_allowance | 50.00   | 16.00   | 25.00    | 0.00     | 0.00    | 9.00
      bill_info_with_progressive_discounts  | 30.00   | 15.00   | 15.00    | 0.00     | 0.00    | 0.00
      bill_info_with_penalty                | 100.00  | 120.00  | 0.00     | 0.00     | 20.00   | 0.00
      bill_info_with_interest               | 15.00   | 24.77   | 0.00     | 9.77     | 0.00    | 0.00
      bill_info_with_penalty_and_interest   | 15.00   | 36.00   | 0.00     | 20.00    | 1.00    | 0.00
      """)
  void testBillCalculations(String fixtureFile,
      Double billValue, Double paymentValue, Double discountValue,
      Double interestValue, Double penaltyValue, Double allowanceValue) {
    // given
    System.out.println("Using fixture file: " + fixtureFile);

    double bill = billValue;
    double payment = paymentValue;
    double discount = discountValue;
    double interest = interestValue;
    double penalty = penaltyValue;
    double allowance = allowanceValue;

    // then
    assertEquals(bill - discount - allowance + interest + penalty,
        payment,
        () -> "Bill calculation should be correct");
  }

  @DisplayName("Testing with Value Source")
  @ParameterizedTest
  @ValueSource(strings = {"Hello", "JUnit", "Parameterized", "Test"})
  void testWithValueSource(String input) {
    System.out.println("Input value: " + input);
    assertNotNull(input);
  }

}