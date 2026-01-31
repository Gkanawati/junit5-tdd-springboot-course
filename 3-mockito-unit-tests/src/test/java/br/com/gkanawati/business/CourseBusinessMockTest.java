package br.com.gkanawati.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.gkanawati.services.CourseService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CourseBusinessMockTest {

  CourseService courseService;
  CourseBusiness courseBusiness;
  List<String> allCourses;

  @BeforeEach
  void setUp() {
    courseService = mock(CourseService.class);
    courseBusiness = new CourseBusiness(courseService);

    allCourses = List.of("Spring MVC", "Spring Boot", "Spring Security Fundamentals",
        "API RESTful with Spring", "Java Basics", "OOP", "Docker Essentials");
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

  @Test
  void testMockingList_When_ThrowsException() {
    // given
    List<String> mockedList = mock(List.class);
    when(mockedList.getFirst()).thenThrow(new RuntimeException("Something went wrong"));

    // when / then
    RuntimeException exception = assertThrows(RuntimeException.class, mockedList::getFirst);
    assertEquals("Something went wrong", exception.getMessage());
  }

  @Test
  void testShould_DeleteCoursesNotRelatedToSpring_When_UsingCourseServiceMock() {
    // given
    when(courseService.retrieveCourses("John Doe")).thenReturn(allCourses);

    // when
    courseBusiness.deleteCoursesNotRelatedToSpring("John Doe");

    // then
    verify(courseService).deleteCourse("Docker Essentials");
    verify(courseService, times(1)).deleteCourse("Java Basics");
    verify(courseService, atLeast(1)).deleteCourse("OOP");
    verify(courseService, times(0)).deleteCourse("Spring MVC");
  }

  @Test
  void testShould_DeleteCoursesNotRelatedToSpring_CapturingArguments_When_UsingCourseServiceMock(){
    // given
    when(courseService.retrieveCourses("John Doe")).thenReturn(allCourses);
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    // when
    courseBusiness.deleteCoursesNotRelatedToSpring("John Doe");

    // then
    verify(courseService, times(3)).deleteCourse(captor.capture());

    List<String> deletedCourses = captor.getAllValues();

    assertEquals(3, deletedCourses.size());
    for (String course : deletedCourses) {
      assertFalse(course.contains("Spring"));
    }
  }
}