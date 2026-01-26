package br.com.gkanawati.business;

import br.com.gkanawati.services.CourseService;
import java.util.ArrayList;
import java.util.List;

public class CourseBusiness {

  // SUT -> System Under Test - or Method Under Test
  // CourseBusiness = SUT

  private CourseService courseService;

  public CourseBusiness(CourseService courseService) {
    this.courseService = courseService;
  }

  public List<String> retrieveCoursesRelatedToSpring(String studentName) {
    List<String> courses = courseService.retrieveCourses(studentName);
    List<String> courseRelatedToSpring = new ArrayList<>();
    for (String course : courses) {
      if (course.contains("Spring")) {
        courseRelatedToSpring.add(course);
      }
    }
    return courseRelatedToSpring;
  }
}
