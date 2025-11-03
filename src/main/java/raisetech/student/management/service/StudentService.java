package raisetech.student.management.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.domain.StudentNotFoundException;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<StudentEntity> searchStudentList() {
    return repository.searchStudents();
  }

  public List<StudentEntity> filterStudentList() {
    List<StudentEntity> filteredStudentList = repository.searchStudents().stream()
        .filter(n -> n.getAge() >= 25)
        .collect(Collectors.toList());
    return filteredStudentList;
  }

  @Transactional
  public void newRegisterStudentEntity(StudentDetail newStudentDetail) {
    repository.registerStudentEntity(newStudentDetail.getStudent());
    for (CourseEnrollmentEntity newCourseEnrollment : newStudentDetail.getStudentCourses()) {
      newCourseEnrollment.setStudentId(newStudentDetail.getStudent().getId());
      newCourseEnrollment.setCourseStartAt(String.valueOf(LocalDate.now()));
      repository.registerCourseEnrollment(newCourseEnrollment);
    }
  }

  public List<CourseEnrollmentEntity> filterCourseList() {
    List<CourseEnrollmentEntity> filteredCourseList = repository.searchCourses().stream()
      .filter(n -> Objects.equals("Java Course", n.getCourseName()))
      .collect(Collectors.toList());
    return filteredCourseList;
  }

  public List<CourseEnrollmentEntity> searchStudentCourse() {
    return repository.searchCourses();
  }

//  New service to fetch only ONE student entity
  public StudentDetail searchByStudentId(String id) {
    //Necessary object to check for NPE
    StudentEntity singleStudentEntity = repository.fetchByStudentId(id);
    if (singleStudentEntity == null) {
      throw new StudentNotFoundException("ID" + id + "の受講生を見つかりませんでした。");
    }
    //New line to check for NPE in the list as well
    List<CourseEnrollmentEntity> allCourses = java.util.Objects.requireNonNullElse
        (repository.searchCourses(),Collections.emptyList());
    List<CourseEnrollmentEntity> studentFilterCourses = allCourses.stream()
        .filter(courseEnrollment ->
            Objects.equals(singleStudentEntity.getId(), courseEnrollment.getStudentId()))
        .collect(Collectors.toList());
    return converter.convertSingleStudentDetail(singleStudentEntity, studentFilterCourses);
  }

  @Transactional
  public void updateStudentDetails (StudentDetail studentDetail) {
    repository.updateStudentEntity(studentDetail.getStudent());
  }


}
