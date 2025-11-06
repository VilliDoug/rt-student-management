package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import raisetech.student.management.controller.converter.MainConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.domain.StudentNotFoundException;
import raisetech.student.management.service.MainService;

@RestController
public class MainController {

  private MainService service;
  private MainConverter converter;

  @Autowired
  public MainController(MainService service, MainConverter converter) {
    this.service = service;
    this.converter = converter;
  }
//new rest thing
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<Course> studentCourses = service.searchStudentCourse();

    return converter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/courseList")
  public String getStudentCourse(Model model) {
    model.addAttribute("courseList", service.searchStudentCourse());
    return "courseList";
  }

  @GetMapping("/filterStudentList")
  public List<Student> getFilteredStudents() {
    return service.filterStudentList();
  }

  @GetMapping("/filterCourseList")
  public List<Course> getFilteredCourses() {
    return service.filterCourseList();
  }

  @GetMapping("/registerStudentStart")
  public String registerStudentStart(Model model) {
    model.addAttribute("newStudent", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail newStudent, BindingResult result) {
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

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudentDetails(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

}
