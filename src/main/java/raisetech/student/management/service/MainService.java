package raisetech.student.management.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.Converter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.domain.StudentNotFoundException;
import raisetech.student.management.repository.Repository;

@Service
public class MainService {

  private Repository repository;

  @Autowired
  private Converter converter;

  @Autowired
  public MainService(Repository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchStudent();
  }

  public List<Student> filterStudentList() {
    List<Student> filteredStudentList = repository.searchStudent().stream()
        .filter(n -> n.getAge() >= 25)
        .collect(Collectors.toList());
    return filteredStudentList;
  }

  @Transactional
  public void newRegisterStudentEntity(StudentDetail newStudentDetail) {
    repository.registerStudentEntity(newStudentDetail.getStudent());
    for (Course newCourseEnrollment : newStudentDetail.getStudentCourses()) {
      newCourseEnrollment.setStudentId(newStudentDetail.getStudent().getId());
      newCourseEnrollment.setCourseStartAt(String.valueOf(LocalDate.now()));
      repository.registerCourseEnrollment(newCourseEnrollment);
    }
  }

  public List<Course> filterCourseList() {
    List<Course> filteredCourseList = repository.searchCourse().stream()
      .filter(n -> Objects.equals("Java Course", n.getCourseName()))
      .collect(Collectors.toList());
    return filteredCourseList;
  }

  public List<Course> searchStudentCourse() {
    return repository.searchCourse();
  }

//  New service to fetch only ONE student entity
  public StudentDetail searchByStudentId(String id) {
    //Necessary object to check for NPE
    Student singleStudent = repository.fetchById(id);
    if (singleStudent == null) {
      throw new StudentNotFoundException("ID" + id + "の受講生を見つかりませんでした。");
    }
    //New line to check for NPE in the list as well
    List<Course> allCourses = java.util.Objects.requireNonNullElse
        (repository.searchCourse(),Collections.emptyList());
    List<Course> studentFilterCourses = allCourses.stream()
        .filter(courseEnrollment ->
            Objects.equals(singleStudent.getId(), courseEnrollment.getStudentId()))
        .collect(Collectors.toList());
    return converter.convertSingleStudentDetail(singleStudent, studentFilterCourses);
  }

  @Transactional
  public void updateStudentDetails (StudentDetail studentDetail) {
    repository.updateStudentEntity(studentDetail.getStudent());
  }


}
