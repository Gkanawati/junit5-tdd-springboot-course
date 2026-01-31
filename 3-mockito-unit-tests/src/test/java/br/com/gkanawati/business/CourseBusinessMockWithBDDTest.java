package br.com.gkanawati.business;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import br.com.gkanawati.services.CourseService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseBusinessMockWithBDDTest {

  CourseService courseService;
  CourseBusiness courseBusiness;
  List<String> allCourses;

  @BeforeEach
  void setUp() {
    courseService = mock(CourseService.class);
    courseBusiness = new CourseBusiness(courseService);

    allCourses = List.of("Spring MVC", "Spring Boot", "Spring Security Fundamentals",
        "API RESTful with Spring");
  }

  @Test
  void testShould_ReturnCoursesRelatedToSpring_When_UsingCourseServiceMock() {
    // given

    // BDD -> Behavior-Driven Development - Desenvolvimento Orientado a Comportamento
    // With BDD: instead
    // when -> given
    // thenReturn -> willReturn
    given(courseService.retrieveCourses("John Doe")).willReturn(allCourses);

    // when
    var courses = courseBusiness.retrieveCoursesRelatedToSpring("John Doe");

    // then
    assertThat(courses.size(), is(4));
  }

}