package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;
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
    List<StudentEntity> students = service.searchStudentList();
    List<CourseEnrollmentEntity> studentCourses = service.searchStudentCourse();

    return converter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/courseList")
  public List<CourseEnrollmentEntity> getStudentCourse() {
    return service.searchStudentCourse();
  }

  @GetMapping("/filterStudentList")
  public List<StudentEntity> getFilteredStudents() {
    return service.filterStudentList();
  }

  @GetMapping("/filterCourseList")
  public List<CourseEnrollmentEntity> getFilteredCourses() {
    return service.filterCourseList();
  }

}
