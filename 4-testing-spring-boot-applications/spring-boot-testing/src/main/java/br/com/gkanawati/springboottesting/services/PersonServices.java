package br.com.gkanawati.springboottesting.services;

import br.com.gkanawati.springboottesting.exceptions.ResourceNotFoundException;
import br.com.gkanawati.springboottesting.model.Person;
import br.com.gkanawati.springboottesting.repositories.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServices {

  private Logger logger = Logger.getLogger(PersonServices.class.getName());

  @Autowired
  PersonRepository repository;

  public List<Person> findAll() {
    logger.info("Finding all people!");

    return repository.findAll();
  }

  public Person findById(Long id) {
    logger.info("Finding one person!");

    return repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
  }

  public Person create(Person person) {
    logger.info("Creating one person!");

    Optional<Person> existingPerson = repository.findByEmail(person.getEmail());
    if (existingPerson.isPresent()) {
      throw new IllegalArgumentException("A person with this email already exists!");
    }

    return repository.save(person);
  }

  public Person update(Person person) {
    logger.info("Updating one person!");

    var entity = repository.findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setEmail(person.getEmail());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return repository.save(entity);
  }

  public void delete(Long id) {
    logger.info("Deleting one person!");

    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    repository.delete(entity);
  }
}
