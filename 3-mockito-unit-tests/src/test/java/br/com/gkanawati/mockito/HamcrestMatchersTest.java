package br.com.gkanawati.mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class HamcrestMatchersTest {

  @Test
  void testHamcrestListMatchers() {
    // given
    List<Integer> scores = Arrays.asList(99, 100, 101, 105);

    // when & then
    assertThat(scores, hasSize(4));
    assertThat(scores, hasItems(100, 101));
    assertThat(scores, everyItem(greaterThan(98)));
    assertThat(scores, everyItem(lessThanOrEqualTo(105)));
  }

  @Test
  void testHamcrestStringMatchers() {
    // given
    String str = "Learn Hamcrest Matchers in Java";

    // when & then
    assertThat(str, startsWith("Learn"));
    assertThat(str, endsWith("Java"));
    assertThat(str, containsString("Hamcrest"));
  }

  // Arrays
  @Test
  void testHamcrestArraysMatchers() {
    // given
    String[] fruits = {"Apple", "Banana", "Orange", "Mango"};

    // when & then
    assertThat(fruits, arrayWithSize(4));
    assertThat(fruits, arrayContaining("Apple", "Banana", "Orange", "Mango"));
    assertThat(fruits, arrayContainingInAnyOrder(fruits));
  }

}
