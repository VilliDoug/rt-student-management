package raisetech.student.management.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    // 検索処理
    //　絞り込みをする。年齢が25歳以上の人のみを抽出する。
    //　抽出したリストをコントローラーに渡す。
    return repository.searchStudents();
  }

  public List<Student> filterStudentList() {
    List<Student> filteredStudentList = repository.searchStudents().stream()
        .filter(n -> n.getAge() >= 25)
        .collect(Collectors.toList());
    return filteredStudentList;
  }

  public List<StudentCourse> filterCourseList() {
    List<StudentCourse> filteredCourseList = repository.searchCourses().stream()
      .filter(n -> Objects.equals("Java Course", n.getCourseName()))
      .collect(Collectors.toList());
    return filteredCourseList;
  }

  public List<StudentCourse> searchStudentCourse() {
    //　抽出絞り込み検索で「何かコース」のコース情報のみを抽出する。
    //　抽出したリストをコントローラーに渡す。
    return repository.searchCourses();
  }


}
