package br.com.gkanawati;

import java.util.concurrent.atomic.AtomicLong;

public class PersonServiceImpl implements PersonService {

  /**
   * {@inheritDoc}
   */
  @Override
  public Person createPerson(Person person) {
    var id = new AtomicLong().incrementAndGet();
    person.setId(id);

    if (person.getEmail() == null || person.getEmail().isBlank()) {
      throw new IllegalArgumentException("Email cannot be null");
    }

    if (person.getFirstName() == null || person.getFirstName().isBlank()) {
      throw new IllegalArgumentException("First name cannot be null");
    }

    if (person.getLastName() == null || person.getLastName().isBlank()) {
      throw new IllegalArgumentException("Last name cannot be null");
    }

    return person;
  }

}
