package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;
import raisetech.student.management.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<StudentEntity> students = service.searchStudentList();
    List<CourseEnrollmentEntity> studentCourses = service.searchStudentCourse();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
  }

  @GetMapping("/courseList")
  public String getStudentCourse(Model model) {
    model.addAttribute("courseList", service.searchStudentCourse());
    return "courseList";
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
