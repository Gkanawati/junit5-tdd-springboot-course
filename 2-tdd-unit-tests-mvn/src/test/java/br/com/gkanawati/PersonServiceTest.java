package br.com.gkanawati;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonServiceTest {

  // TDD -> os testes vem primeiro do que os metodos
  // Ciclo:
  // 1. Red -> escreva um teste que falha
  // 2. Green -> escreva o codigo minimo para fazer o teste passar
  // 3. Refactor -> refatore o codigo mantendo os testes passando

  @Test
  @DisplayName("When create a Person with success should return the Person object")
  void testCreatePerson_WhenSuccess_ShouldReturnPersonObject() {
    // given
    PersonService personService = new PersonServiceImpl();

    Person person = new Person("John", "Doe", "Rua das Laranjeiras", "M", "johndoe@email.com");

    // when
    Person actual = personService.createPerson(person);

    // then
    assertNotNull(actual, () -> "The created person should not be null");
  }

}
