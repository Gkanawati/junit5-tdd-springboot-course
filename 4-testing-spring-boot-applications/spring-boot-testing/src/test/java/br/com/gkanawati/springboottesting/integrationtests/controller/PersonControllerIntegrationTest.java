package br.com.gkanawati.springboottesting.integrationtests.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import br.com.gkanawati.springboottesting.config.TestConfigs;
import br.com.gkanawati.springboottesting.controllers.PersonController;
import br.com.gkanawati.springboottesting.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.gkanawati.springboottesting.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Integration tests for {@link PersonController}
 */
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class PersonControllerIntegrationTest extends AbstractIntegrationTest {

  private static RequestSpecification requestSpecification;
  private static ObjectMapper objectMapper;
  private static Person person;

  @BeforeAll
  static void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    requestSpecification = new RequestSpecBuilder()
        .setBasePath("/person")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    person = new Person("John", "Doe", "johndoe@example", "Sao Paulo", "Male");
  }

  @Test
  @Order(1)
  @DisplayName("When create person then return created person")
  void test_when_createPerson_thenReturnCreatedPerson() throws JsonProcessingException {
    // given - when - then
    String content = given()
        .spec(requestSpecification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(person)
        .when()
        .post()
        .then()
        .statusCode(201)
        .extract().body().asString();

    Person createdPerson = objectMapper.readValue(content, Person.class);

    // Save the generated id so subsequent tests (update, delete) can use it
    person.setId(createdPerson.getId());

    // Pre-condition: fails fast if null, avoiding NullPointerExceptions inside assertAll
    assertNotNull(createdPerson);
    // assertAll executes all assertions even if some fail, reporting all failures at once
    assertAll(
        () -> assertNotNull(createdPerson.getId()),
        () -> assertEquals(person.getFirstName(), createdPerson.getFirstName()),
        () -> assertEquals(person.getLastName(), createdPerson.getLastName()),
        () -> assertEquals(person.getEmail(), createdPerson.getEmail()),
        () -> assertEquals(person.getGender(), createdPerson.getGender())
    );
  }

  @Test
  @Order(2)
  @DisplayName("when update Person then return updated Person")
  void test_when_updatePerson_thenReturnUpdatedPerson() throws JsonProcessingException {
    assumeTrue(person.getId() != null, "Create test must run first (run the full test class)");
    // given - when - then
    person.setFirstName("Fulano");
    person.setLastName("Smith");
    person.setEmail("fulanosmith@example");

    String content = given()
        .spec(requestSpecification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(person)
        .when()
        .put()
        .then()
        .statusCode(200)
        .extract().body().asString();

    Person updatedPerson = objectMapper.readValue(content, Person.class);

    assertNotNull(updatedPerson);
    assertAll(
        () -> assertEquals(person.getId(), updatedPerson.getId()),
        () -> assertEquals(person.getFirstName(), updatedPerson.getFirstName()),
        () -> assertEquals(person.getLastName(), updatedPerson.getLastName()),
        () -> assertEquals(person.getEmail(), updatedPerson.getEmail()),
        () -> assertEquals(person.getGender(), updatedPerson.getGender())
    );
  }

  @Test
  @Order(3)
  @DisplayName("when find person by id then return person")
  void test_when_findById_thenReturnPerson() throws JsonProcessingException {
    assumeTrue(person.getId() != null, "Create test must run first (run the full test class)");
    // given - when - then
    String content = given()
        .spec(requestSpecification)
        .pathParam("id", person.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(200)
        .extract().body().asString();

    Person foundPerson = objectMapper.readValue(content, Person.class);

    assertNotNull(foundPerson);
    assertAll(
        () -> assertEquals(person.getId(), foundPerson.getId()),
        () -> assertEquals(person.getFirstName(), foundPerson.getFirstName()),
        () -> assertEquals(person.getLastName(), foundPerson.getLastName()),
        () -> assertEquals(person.getEmail(), foundPerson.getEmail()),
        () -> assertEquals(person.getGender(), foundPerson.getGender())
    );
  }

  @Test
  @Order(4)
  @DisplayName("when find all persons then return list of persons")
  void test_findAll_thenReturnListOfPersons() throws JsonProcessingException {
    var person2 = new Person("Jane", "Doe", "janedoe@example", "Rio de Janeiro", "Female");

    given()
        .spec(requestSpecification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(person2)
        .when()
        .post()
        .then()
        .statusCode(201);

    // given - when - then
    String content = given()
        .spec(requestSpecification)
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract().body().asString();

    Person[] people = objectMapper.readValue(content, Person[].class);

    assertNotNull(people);
    assertTrue(people.length > 0);
    assertAll(
        () -> assertTrue(people.length >= 2),
        () -> assertTrue(Arrays.stream(people).anyMatch(p -> p.getId().equals(person.getId()))),
        () -> assertTrue(
            Arrays.stream(people).anyMatch(p -> p.getEmail().equals(person2.getEmail())))
    );

    // with hamcrest matchers
    assertThat(Arrays.asList(people), allOf(
        hasItem(hasProperty("id", equalTo(person.getId()))),
        hasItem(hasProperty("firstName", equalTo(person.getFirstName()))),
        hasItem(hasProperty("firstName", equalTo(person2.getFirstName()))),
        hasItem(hasProperty("email", equalTo(person.getEmail()))),
        hasItem(hasProperty("email", equalTo(person2.getEmail())))
    ));
  }

  @Test
  @Order(5)
  @DisplayName("when delete person then person is removed")
  void test_when_deletePerson_thenPersonIsRemoved() {
    assumeTrue(person.getId() != null, "Create test must run first (run the full test class)");
    // given - when - then
    given()
        .spec(requestSpecification)
        .pathParam("id", person.getId())
        .when()
        .delete("{id}")
        .then()
        .statusCode(204);
  }

}
