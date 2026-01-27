package br.com.gkanawati.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
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

  @Test
  void testMockingList_Size() {
    // given
    List<?> mockedList = mock(List.class);
    when(mockedList.size()).thenReturn(999).thenReturn(100);

    // when
    int size1 = mockedList.size();
    int size2 = mockedList.size(); // multiple calls

    // then
    assertEquals(999, size1);
    assertEquals(100, size2);
  }

  @Test
  void testMockingList_GetMustReturn_DifferentValues() {
    // given
    List<String> mockedList = mock(List.class);
    when(mockedList.getFirst()).thenReturn("First Call").thenReturn("Second Call");

    // when
    String firstCall = mockedList.getFirst();
    String secondCall = mockedList.getFirst(); // multiple calls

    // then
    assertEquals("First Call", firstCall);
    assertEquals("Second Call", secondCall);
  }

  @Test
  void testMockingList_GetIsCalledWith_ArgumentMatchers() {
    // given
    List<String> mockedList = mock(List.class);
    when(mockedList.get(anyInt())).thenReturn("Element");

    // when / then
    assertEquals("Element", mockedList.get(anyInt()));
    assertEquals("Element", mockedList.get(999));
  }

}