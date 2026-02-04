package br.com.gkanawati.springboottesting.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.gkanawati.springboottesting.model.Person;
import br.com.gkanawati.springboottesting.services.PersonServices;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    person = new Person("John", "Doe", "johndoe@example", "Sao Paulo", "Male");
  }

  @Test
  @DisplayName("Given Person Object When Save Then Return Saved Person")
  void testGivenPersonObject_WhenSave_ThenReturnSavedPerson() throws Exception {
    // given
    when(personService.create(any(Person.class)))
        .thenReturn(new Person(1L, "John", "Doe", "johndoe@example",
            "Sao Paulo", "Male"));

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

}