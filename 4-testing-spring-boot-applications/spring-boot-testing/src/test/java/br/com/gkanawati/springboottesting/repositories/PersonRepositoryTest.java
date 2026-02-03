package br.com.gkanawati.springboottesting.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.gkanawati.springboottesting.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersonRepositoryTest {

  @Autowired
  PersonRepository personRepository;

  private Person person;

  @BeforeEach
  void setUp() {
    person = new Person("John", "Doe", "johndoe@example", "Sao Paulo", "Male");
  }

  @Test
  @DisplayName("Given Person Object When Save Then Return Saved Person")
  void testGivenPersonObject_WhenSave_ThenReturnSavedPerson() {
    // given
    // when
    Person savedPerson = personRepository.save(person);

    // then
    assertNotNull(savedPerson);
    assertTrue(savedPerson.getId() > 0);
  }

  @Test
  @DisplayName("Given Person List When Find All Then Return Person List")
  void testGivenPersonList_whenFindAll_thenReturnPersonList() {
    // given
    Person person2 = new Person("Jane", "Doe", "janedoe@example", "Rio de Janeiro", "Female");
    personRepository.save(person);
    personRepository.save(person2);

    // when
    var personList = personRepository.findAll();

    // then
    assertNotNull(personList);
    assertEquals(2, personList.size());
  }

  @Test
  @DisplayName("Given Person Object When Find By ID Then Return Person Object")
  void testGivenPersonObject_whenFindByID_thenReturnPersonObject() {
    // given
    personRepository.save(person);

    // when
    var optionalPerson = personRepository.findById(person.getId());

    // then
    assertTrue(optionalPerson.isPresent());
    assertEquals(person.getId(), optionalPerson.get().getId());
    assertEquals(person.getFirstName(), optionalPerson.get().getFirstName());
  }

  @Test
  @DisplayName("Given Person Object When Find By Email Then Return Person Object")
  void testGivenPersonObject_whenFindByEmail_thenReturnPersonObject() {
    // given
    personRepository.save(person);

    // when
    var optionalPerson = personRepository.findByEmail(person.getEmail());

    // then
    assertTrue(optionalPerson.isPresent());
    assertEquals(person.getId(), optionalPerson.get().getId());
    assertEquals(person.getFirstName(), optionalPerson.get().getFirstName());
  }

  @Test
  @DisplayName("Given Person Object When Update Then Return Updated Person")
  void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPerson() {
    // given
    personRepository.save(person);

    // when
    Person savedPerson = personRepository.findById(person.getId()).orElseThrow();
    savedPerson.setFirstName("Johnny");
    Person updatedPerson = personRepository.save(savedPerson);

    // then
    assertEquals("Johnny", updatedPerson.getFirstName());
    assertEquals(savedPerson.getId(), updatedPerson.getId());
  }

  @Test
  @DisplayName("Given Person Object When Delete Then Remove Person")
  void testGivenPersonObject_whenDelete_thenRemovePerson() {
    // given
    personRepository.save(person);

    // when
    personRepository.delete(person);
    var optionalPerson = personRepository.findById(person.getId());

    // then
    assertTrue(optionalPerson.isEmpty());
  }

  @Test
  void testGivenPersonObject_whenFindByJPQL_ThenReturnPersonObject() {
    // given
    personRepository.save(person);

    // when
    Person foundPerson = personRepository.findByJPQL("John", "Doe");

    // then
    assertNotNull(foundPerson);
    assertEquals(person.getId(), foundPerson.getId());
  }

  @Test
  void testGivenPersonObject_whenFindByJPQL_ThenNotReturnPersonObject() {
    // given
    personRepository.save(person);

    // when
    Person foundPerson = personRepository.findByJPQL("NOT", "FOUND");

    // then
    assertNull(foundPerson);
  }

  @Test
  @DisplayName("Given Person Object When Find By JPQL Named Parameters Then Return Person Object")
  void testGivenPersonObject_whenFindByJPQLNamedParameters_ThenReturnPersonObject() {
    // given
    personRepository.save(person);

    // when
    Person foundPerson = personRepository.findByJPQLNamedParameters("John", "Doe");

    // then
    assertNotNull(foundPerson);
    assertEquals(person.getId(), foundPerson.getId());
  }

  @Test
  @DisplayName("Given Person Object When Find By Native SQL Then Return Person Object")
  void testGivenPersonObject_whenFindByNativeSQL_ThenReturnPersonObject() {
    // given
    personRepository.save(person);

    // when
    Person foundPerson = personRepository.findByNativeSQL("John", "Doe");

    // then
    assertNotNull(foundPerson);
    assertEquals(person.getId(), foundPerson.getId());
  }

  @Test
  @DisplayName("Given Person Object When Find By Native SQL with Named Parameters Then Return Person Object")
  void testGivenPersonObject_whenFindByNativeSQLwithNamedParameters_ThenReturnPersonObject() {
    // given
    personRepository.save(person);

    // when
    Person foundPerson = personRepository.findByNativeSQLwithNamedParameters("John", "Doe");

    // then
    assertNotNull(foundPerson);
    assertEquals(person.getId(), foundPerson.getId());
  }

}