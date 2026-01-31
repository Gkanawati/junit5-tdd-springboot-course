package br.com.gkanawati.services;

import java.util.List;

public interface CourseService {

  List<String> retrieveCourses(String studentName);
  void deleteCourse(String studentName);

}
