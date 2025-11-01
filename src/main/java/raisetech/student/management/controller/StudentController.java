package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;
import raisetech.student.management.domain.StudentDetail;
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

  @GetMapping("/registerStudentStart")
  public String registerStudentStart(Model model) {
    model.addAttribute("newStudent", new StudentDetail());
    return "registerStudent";
  }


  //＠Validは現在のDependencyに入っていないため、インストールするよりこのままで実装したかった。
  //正直ここまでのコードは理解しやすかったんですが、ほぼAIの力で書きました。
  //一つの実装をするだけで五つ以上の見たことないことを追加。
  //不満です。
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail newStudent, BindingResult result) {
    String studentNameCheck = newStudent.getStudent().getName();
    String studentCourseCheck = newStudent.getStudentCourses().getFirst().getCourseName();
    if (studentNameCheck == null || studentNameCheck.trim().isEmpty()) {
      result.rejectValue("student.name", "name.required", "受講生の名前は必要です");
    }
    if (studentCourseCheck == null || studentCourseCheck.trim().isEmpty()) {
      result.rejectValue("studentCourses[0].courseName", "courseName.required", "受講生コースは必要です");
    }
    if (result.hasErrors()) {
      return "registerStudent";
    }
    service.newRegisterStudentEntity(newStudent);
    return "redirect:/studentList";
  }

  @GetMapping("/homePage")
  public String goHomePage(Model model) {
    return "homePage";
  }

}
