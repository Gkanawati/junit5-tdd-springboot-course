package br.com.gkanawati.springboottesting.controllers;

import br.com.gkanawati.springboottesting.model.Person;
import br.com.gkanawati.springboottesting.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
@Tag(name = "Person", description = "Endpoints for managing persons")
public class PersonController {

  @Autowired
  private PersonServices service;

  @Operation(summary = "Find all persons",
      responses = {
          @ApiResponse(responseCode = "200", description = "Success")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Person> findAll() {
    return service.findAll();
  }

  @Operation(summary = "Find a person by ID",
      responses = {
          @ApiResponse(responseCode = "200", description = "Success"),
          @ApiResponse(responseCode = "404", description = "Person not found")
      })
  @GetMapping(value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Person> findById(@PathVariable Long id) {
    try {
      var person = service.findById(id);
      return ResponseEntity.ok(person);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Create a new person",
      responses = {
          @ApiResponse(responseCode = "201", description = "Person created")
      })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
    try {
      var createdPerson = service.create(person);
      return ResponseEntity.created(URI.create("/person/" + createdPerson.getId()))
          .body(createdPerson);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(summary = "Update a person",
      responses = {
          @ApiResponse(responseCode = "200", description = "Person updated"),
          @ApiResponse(responseCode = "404", description = "Person not found")
      })
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Person update(@RequestBody Person person) {
    return service.update(person);
  }

  @Operation(summary = "Delete a person by ID",
      responses = {
          @ApiResponse(responseCode = "204", description = "Person deleted"),
          @ApiResponse(responseCode = "404", description = "Person not found")
      })
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}