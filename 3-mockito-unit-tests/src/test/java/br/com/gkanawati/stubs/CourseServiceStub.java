package br.com.gkanawati.stubs;

import br.com.gkanawati.services.CourseService;
import java.util.List;

public class CourseServiceStub implements CourseService {

  @Override
  public List<String> retrieveCourses(String studentName) {
    return List.of(
        "Spring MVC",
        "Spring Boot",
        "API RESTful with Spring",
        "Java Basics",
        "Java Advanced Topics",
        "Docker Essentials",
        "Kubernetes for Beginners",
        "Microservices Architecture",
        "Hibernate and JPA",
        "Thymeleaf Templating Engine",
        "Spring Security Fundamentals",
        "Testing with JUnit and Mockito",
        "Cloud Deployment with AWS"
    );
  }

  @Override
  public void deleteCourse(String studentName) {
  }

}
