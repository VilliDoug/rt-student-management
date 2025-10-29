package raisetech.student.management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCourse();

    return converter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/courseList")
  public List<StudentCourse> getStudentCourse() {
    return service.searchStudentCourse();
  }

  @GetMapping("/filterStudentList")
  public List<Student> getStuFilterList() {
    return service.filterStudentList();
  }

  @GetMapping("/filterCourseList")
  public List<StudentCourse> getCourFilterList() {
    return service.filterCourseList();
  }

}
