package br.com.gkanawati.springboottesting.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
  @DisplayName("Given Person List When Find All Then Return Person List")
  void testGivenPersonList_whenFindAll_thenReturnPersonList() {
    // given
    when(personRepository.findAll()).thenReturn(
        java.util.List.of(
            personWithId,
            new Person(2L, "Jane", "Doe", "janedoe@example", "Rio de Janeiro", "Female")
        )
    );

    // when
    var personList = personService.findAll();

    // then
    assertNotNull(personList);
    assertEquals(2, personList.size());
  }

  @Test
  @DisplayName("Given Person ID When Find By ID Then Return Person Object")
  void testGivenPersonId_whenFindById_thenReturnPersonObject() {
    // given
    when(personRepository.findById(1L)).thenReturn(Optional.of(personWithId));

    // when
    var foundPerson = personService.findById(1L);

    // then
    assertNotNull(foundPerson);
    assertEquals(1L, foundPerson.getId());
    assertEquals("John", foundPerson.getFirstName());
  }

  @Test
  @DisplayName("Given Invalid Person ID When Find By ID Then Throw Exception")
  void testGivenInvalidPersonId_whenFindById_thenThrowException() {
    // given
    when(personRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    var exception = assertThrows(
        RuntimeException.class,
        () -> personService.findById(1L)
    );

    // then
    assertEquals("No records found for this ID!", exception.getMessage());
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

  @Test
  @DisplayName("Given Existing Email When Save Person then Throw Exception")
  void testGivenExistingEmail_WhenSavePerson_thenThrowException() {
    // given
    when(personRepository.findByEmail(anyString())).thenReturn((Optional.of(personWithId)));

    // when
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> personService.create(person)
    );

    // then
    assertEquals("A person with this email already exists!", exception.getMessage());
    verify(personRepository, times(0)).save(personWithId);
  }

  @Test
  @DisplayName("Given Person Object When Update Person then Return Updated Person")
  void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatedPerson() {
    // given
    Person existingPerson = new Person(1L, "OldJohn", "Doe", "johndoe@example", "Sao Paulo",
        "Male");
    Person updatePersonInput = new Person(1L, "NewJohn", "Doe", "newjohndoe@example", "Sao Paulo",
        "Male");

    when(personRepository.findById(1L)).thenReturn(Optional.of(existingPerson));
    when(personRepository.save(existingPerson)).thenReturn(updatePersonInput);

    // when
    Person updatedPerson = personService.update(updatePersonInput);

    // then
    assertNotNull(updatedPerson);
    assertEquals("NewJohn", updatedPerson.getFirstName());
    assertEquals("newjohndoe@example", updatedPerson.getEmail());
  }

  @Test
  void testGivenInvalidPersonObject_WhenUpdatePerson_thenThrowException() {
    // given
    when(personRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    var exception = assertThrows(
        RuntimeException.class,
        () -> personService.update(personWithId)
    );

    // then
    assertEquals("No records found for this ID!", exception.getMessage());
  }

  @Test
  @DisplayName("Given Person ID When Delete Then Remove Person")
  void testGivenPersonId_WhenDelete_thenRemovePerson() {
    // given
    when(personRepository.findById(1L)).thenReturn(Optional.of(personWithId));
    // doNothing().when(personRepository).delete(personWithId);
    // doNothing() is the default behavior for void methods in Mockito, so it's not strictly necessary here.

    // when
    personService.delete(1L);

    // then
    verify(personRepository, times(1)).delete(personWithId);
  }

  @Test
  @DisplayName("Given Invalid Person ID When Delete Then Throw Exception")
  void testGivenInvalidPersonId_WhenDelete_thenThrowException() {
    // given
    when(personRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    var exception = assertThrows(
        RuntimeException.class,
        () -> personService.delete(1L)
    );

    // then
    assertEquals("No records found for this ID!", exception.getMessage());
  }

}