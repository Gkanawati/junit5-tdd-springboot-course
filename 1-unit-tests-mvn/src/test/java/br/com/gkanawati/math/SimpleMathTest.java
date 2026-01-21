package br.com.gkanawati.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SimpleMathTest {

  @Test
  void sum() {
    SimpleMath simpleMath = new SimpleMath();
    Double actual = simpleMath.sum(6.2D, 2D);
    Double expected = 8.2D;

    assertEquals(expected, actual);
  }

  @Test
  void subtraction() {
  }

  @Test
  void multiplication() {
  }

  @Test
  void division() {
  }

  @Test
  void mean() {
  }

  @Test
  void squareRoot() {
  }
}