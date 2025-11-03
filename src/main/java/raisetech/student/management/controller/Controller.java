package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import raisetech.student.management.controller.converter.Converter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.domain.StudentNotFoundException;
import raisetech.student.management.service.Service;

@org.springframework.stereotype.Controller
public class Controller {

  private Service service;
  private Converter converter;

  @Autowired
  public Controller(Service service, Converter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<Course> studentCourses = service.searchStudentCourse();
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
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

  @GetMapping("/updateStudent")
  public String goUpdateStudent(@RequestParam String id, Model model) {
    StudentDetail matchStudentId = service.searchByStudentId(id);
    model.addAttribute("updateStudentDetail", matchStudentId);
    return "updateStudent";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail updateStudentEntity, BindingResult result) {
    if (result.hasErrors()) {
      return "updateStudent";
    }
    service.updateStudentDetails(updateStudentEntity);
    return "redirect:/studentList";
  }
  //here we go again lads. What even is RedirectAttributes?
  @ExceptionHandler(StudentNotFoundException.class)
  public String handleNotFoundException(StudentNotFoundException ex, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
    return "redirect:/studentList";
  }

}
