package br.com.gkanawati;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonServiceTest {

  // TDD -> os testes vem primeiro do que os metodos
  // Ciclo:
  // 1. Red -> escreva um teste que falha
  // 2. Green -> escreva o codigo minimo para fazer o teste passar
  // 3. Refactor -> refatore o codigo mantendo os testes passando

  Person person;

  @BeforeEach
  void setUp() {
    person = new Person("John", "Doe", "Rua das Laranjeiras", "M",
        "johndoe@email.com");
  }

  @Test
  @DisplayName("When create a Person with success should return the Person object")
  void testCreatePerson_WhenSuccess_ShouldReturnPersonObject() {
    // given
    PersonService personService = new PersonServiceImpl();

    // when
    Person actual = personService.createPerson(person);

    // then
    assertNotNull(actual.getId(), () -> "The created person should contains id");
    assertNotNull(actual, () -> "The created person should not be null");
    assertNotNull(actual.getFirstName(), () -> "The created person should contains first name");
    assertNotNull(actual.getLastName(), () -> "The created person should contains last name");
  }

  @Test
  @DisplayName("When create a Person with null email should throw IllegalArgumentException")
  void testCreatePerson_WithNullEmail_ShouldThrowIllegalArgumentException() {
    // given
    PersonService personService = new PersonServiceImpl();
    person.setEmail(null);

    // when / then
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          personService.createPerson(person);
        },
        () -> "The createPerson method should throw IllegalArgumentException when email is null");

    assertEquals("Email cannot be null", exception.getMessage(),
        () -> "The exception message should be 'Email cannot be null'");
  }


}
