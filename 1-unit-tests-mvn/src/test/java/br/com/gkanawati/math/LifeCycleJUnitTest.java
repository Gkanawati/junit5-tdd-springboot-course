package br.com.gkanawati.math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Life Cycle JUnit Test")
class LifeCycleJUnitTest {

  StringBuilder actualValue = new StringBuilder();

  @AfterEach
  void afterEach() {
    System.out.println("AfterEach executed: " + actualValue);

    // Ciclo de vida default do JUnit -> Uma nova instância da classe de teste é criada para cada metodo de teste.
    // resultado no afterEach:
    // AfterEach executed: A
    // AfterEach executed: B
    // AfterEach executed: C
    // AfterEach executed: D

    // Com o @TestInstance(TestInstance.Lifecycle.PER_CLASS) -> + Utilizado em testes de integracao
    // Uma única instância da classe de teste é criada e compartilhada entre todos os métodos de teste.
    // resultado no afterEach:
    // AfterEach executed: A
    // AfterEach executed: AB
    // AfterEach executed: ABC
    // AfterEach executed: ABCD
  }

  @Test
  void testA() {
    actualValue.append("A");
  }

  @Test
  void testB() {
    actualValue.append("B");
  }

  @Test
  void testC() {
    actualValue.append("C");
  }

  @Test
  void testD() {
    actualValue.append("D");
  }

}