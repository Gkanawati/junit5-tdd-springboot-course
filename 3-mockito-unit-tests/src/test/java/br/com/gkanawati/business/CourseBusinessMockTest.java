package br.com.gkanawati.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.gkanawati.services.CourseService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseBusinessMockTest {

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
    when(courseService.retrieveCourses("John Doe")).thenReturn(allCourses);

    // when
    var courses = courseBusiness.retrieveCoursesRelatedToSpring("John Doe");

    // then
    assertEquals(4, courses.size());
  }

}