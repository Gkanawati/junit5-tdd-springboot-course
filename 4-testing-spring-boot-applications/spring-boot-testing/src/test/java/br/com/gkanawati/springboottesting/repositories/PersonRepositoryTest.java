package br.com.gkanawati.springboottesting.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.gkanawati.springboottesting.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersonRepositoryTest {

  @Autowired
  PersonRepository personRepository;

  @Test
  @DisplayName("Given Person Object When Save Then Return Saved Person")
  void testGivenPersonObject_WhenSave_ThenReturnSavedPerson() {
    // given
    Person person = new Person("John", "Doe", "johndoe@example.com", "Sao Paulo", "Male");

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
    Person person1 = new Person("John", "Doe", "johndoe@example", "Sao Paulo", "Male");
    Person person2 = new Person("Jane", "Doe", "janedoe@example", "Rio de Janeiro", "Female");
    personRepository.save(person1);
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
    Person person = new Person("John", "Doe", "johndoe@example", "Sao Paulo", "Male");
    personRepository.save(person);

    // when
    var optionalPerson = personRepository.findById(person.getId());

    // then
    assertTrue(optionalPerson.isPresent());
    assertEquals(person.getId(), optionalPerson.get().getId());
    assertEquals(person.getFirstName(), optionalPerson.get().getFirstName());
  }

}