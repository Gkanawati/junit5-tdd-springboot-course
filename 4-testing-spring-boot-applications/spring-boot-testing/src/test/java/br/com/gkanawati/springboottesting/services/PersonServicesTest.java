package br.com.gkanawati.springboottesting.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import br.com.gkanawati.springboottesting.model.Person;
import br.com.gkanawati.springboottesting.repositories.PersonRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link PersonServices}
 */
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private PersonServices personService;

  private Person person;
  private Person personWithId;

  @BeforeEach
  void setUp() {
    person = new Person("John", "Doe", "johndoe@example", "Sao Paulo", "Male");
    personWithId = new Person(1L, "John", "Doe", "johndoe@example", "Sao Paulo", "Male");
  }

  @Test
  @DisplayName("Given Person Object When Save Person then Return Saved Person")
  void testGivenPersonObject_WhenSavePerson_thenReturnSavedPerson() {
    // given
    when(personRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(personRepository.save(person)).thenReturn(personWithId);

    // when
    Person savedPerson = personService.create(person);

    // then
    assertNotNull(savedPerson);
    assertNotNull(savedPerson.getId());
    assertEquals("John", savedPerson.getFirstName());
  }

}