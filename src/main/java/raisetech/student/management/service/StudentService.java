package raisetech.student.management.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

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

  public List<CourseEnrollmentEntity> filterCourseList() {
    List<CourseEnrollmentEntity> filteredCourseList = repository.searchCourses().stream()
      .filter(n -> Objects.equals("Java Course", n.getCourseName()))
      .collect(Collectors.toList());
    return filteredCourseList;
  }

  public List<CourseEnrollmentEntity> searchStudentCourse() {
    return repository.searchCourses();
  }


}
