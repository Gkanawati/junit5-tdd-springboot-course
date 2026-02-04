package br.com.gkanawati.springboottesting.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.gkanawati.springboottesting.exceptions.ResourceNotFoundException;
import br.com.gkanawati.springboottesting.model.Person;
import br.com.gkanawati.springboottesting.services.PersonServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

// @WebMvcTest -> To test only the web layer (controller) without starting the full
//    application context.
// Unit tests -> only controller layer

// @SpringBootTest -> To test the full application context including all layers
//    (controller, service, repository).
// Integration tests -> all app will be started

/**
 * Unit tests for {@link PersonController}
 */
@WebMvcTest
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private PersonServices personService;

  private Person person;

  @BeforeEach
  void setUp() {
    person = new Person(1L, "John", "Doe", "johndoe@example", "Sao Paulo", "Male");
  }

  @Test
  @DisplayName("Given Person Object When Save Then Return Saved Person")
  void testGivenPersonObject_WhenSave_ThenReturnSavedPerson() throws Exception {
    // given
    when(personService.create(any(Person.class)))
        .thenReturn(person);

    // when
    ResultActions response = mockMvc.perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(person)));

    // then
    response.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(person.getLastName())))
        .andExpect(jsonPath("$.email", is(person.getEmail())));
  }

  @Test
  @DisplayName("Given Person List When Find All Then Return Person List")
  void testGivenPersonList_whenFindAll_thenReturnPersonList() throws Exception {
    // given
    List<Person> personList = List.of(person,
        new Person(2L, "Jane", "Doe", "janedoe@example", "Rio de Janeiro", "Female")
    );
    when(personService.findAll()).thenReturn(personList);

    // when
    ResultActions response = mockMvc.perform(get("/person")
        .contentType(MediaType.APPLICATION_JSON));

    // then
    response.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(personList.size())))
        .andExpect(jsonPath("$[0].firstName", is(personList.get(0).getFirstName())))
        .andExpect(jsonPath("$[1].firstName", is(personList.get(1).getFirstName())));
  }

  @Test
  @DisplayName("Given Person ID When Find By ID Then Return Person Object")
  void testGivenPersonId_whenFindById_thenReturnPersonObject() throws Exception {
    // given
    when(personService.findById(1L))
        .thenReturn(person);

    // when
    ResultActions response = mockMvc.perform(get("/person/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON));

    // then
    response.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(person.getId().intValue())))
        .andExpect(jsonPath("$.firstName", is(person.getFirstName())));
  }

  @Test
  @DisplayName("Given Invalid Person ID When Find By ID Then Return Not Found")
  void testGivenInvalidPersonId_whenFindById_thenReturnNotFound() throws Exception {
    // given
    when(personService.findById(1L))
        .thenThrow(new ResourceNotFoundException("No records found for this ID!"));

    // when
    ResultActions response = mockMvc.perform(get("/person/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON));

    // then
    response.andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given Person Object When Update Then Return Updated Person")
  void testGivenPersonObject_whenUpdate_thenReturnUpdatedPerson() throws Exception {
    // given
    Person updatedPerson = new Person(1L, "John", "Smith",
        "johnsmith@example", "Sao Paulo", "Male");

    when(personService.update(any(Person.class)))
        .thenReturn(updatedPerson);

    // when
    ResultActions response = mockMvc.perform(put("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedPerson)));

    // then
    response.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
        .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
  }

  @Test
  @DisplayName("Given Invalid Person Object When Update Then Return Not Found")
  void testGivenInvalidPersonObject_whenUpdate_thenReturnNotFound() throws Exception {
    // given
    when(personService.update(any(Person.class)))
        .thenThrow(new ResourceNotFoundException("No records found for this ID!"));

    // when
    ResultActions response = mockMvc.perform(put("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(person)));

    // then
    response.andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given Person ID When Delete Then Return No Content")
  void testGivenPersonId_whenDelete_thenReturnNoContent() throws Exception {
    // given
    Long personId = 1L;
    doNothing().when(personService).delete(personId);

    // when
    ResultActions response = mockMvc.perform(delete("/person/{id}", personId)
        .contentType(MediaType.APPLICATION_JSON));

    // then
    response.andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Given Invalid Person ID When Delete Then Return No Content")
  void testGivenInvalidPersonId_whenDelete_thenReturnNoContent() throws Exception {
    // given
    Long personId = 1L;
    doThrow(new ResourceNotFoundException("No records found for this ID!"))
        .when(personService).delete(anyLong());

    // when
    ResultActions response = mockMvc.perform(delete("/person/{id}", personId)
        .contentType(MediaType.APPLICATION_JSON));

    // then
    response.andDo(print())
        .andExpect(status().isNotFound());
  }

}