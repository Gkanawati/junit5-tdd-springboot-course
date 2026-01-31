package br.com.gkanawati.mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import br.com.gkanawati.business.CourseBusiness;
import br.com.gkanawati.services.CourseService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseBusinessMockitoInjectMocksTest {

  @Mock
  CourseService courseService;

  @InjectMocks
  CourseBusiness courseBusiness;

  @Captor
  ArgumentCaptor<String> captor;

  List<String> allCourses;

  @BeforeEach
  void setUp() {
    // -> Using @InjectMocks instead new()
    // courseBusiness = new CourseBusiness(courseService);

    allCourses = List.of("Spring MVC", "Spring Boot", "Spring Security Fundamentals",
        "API RESTful with Spring", "Docker Essentials");
  }

  @Test
  void testShould_ReturnCoursesRelatedToSpring_When_UsingCourseServiceMock() {
    // given
    given(courseService.retrieveCourses("John Doe")).willReturn(allCourses);

    // when
    var courses = courseBusiness.retrieveCoursesRelatedToSpring("John Doe");

    // then
    assertThat(courses.size(), is(4));
  }

  @Test
  void testDeleteCoursesNotRelatedToSpring_CapturingArguments_Should_WorkAsExpected() {
    // given
    given(courseService.retrieveCourses("John Doe")).willReturn(allCourses);

    // -> Using @Captor instead
    // ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    // when
    courseBusiness.deleteCoursesNotRelatedToSpring("John Doe");

    // then
    then(courseService).should(times(1)).deleteCourse(captor.capture());
    assertThat(captor.getAllValues().size(), is(1));
  }

}