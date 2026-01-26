package br.com.gkanawati.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.gkanawati.services.CourseService;
import br.com.gkanawati.stubs.CourseServiceStub;
import org.junit.jupiter.api.Test;

class CourseBusinessStubTest {

  @Test
  void testShould_ReturnCoursesRelatedToSpring_When_UsingCourseServiceStub() {
    // given
    CourseService courseServiceStub = new CourseServiceStub();
    CourseBusiness courseBusiness = new CourseBusiness(courseServiceStub);

    // when
    var courses = courseBusiness.retrieveCoursesRelatedToSpring("John Doe");

    // then
    assertEquals(4, courses.size());
  }

}