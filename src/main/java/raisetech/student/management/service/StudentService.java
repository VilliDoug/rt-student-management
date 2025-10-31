package raisetech.student.management.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;
import raisetech.student.management.domain.StudentDetail;
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

  //　このコードに対して、Geminiを使いながら作成。オブジェクト作り、IDをgetする、
  // 自分から出来なったことをヒント出してくれたりしました。
  //　変数やメソッド呼び出すことはよく違いものを使ったりしていましたが、今回の課題は7・8割ミスなしでした。
  // AIを使いすぎることに不安はありますが、学習モードにしたら逆にいいと思っていります。
  public void newRegisterStudentEntity(StudentDetail newStudentDetail) {
    StudentEntity student = newStudentDetail.getStudent();
    List<CourseEnrollmentEntity> enrollment = newStudentDetail.getStudentCourses();
    repository.registerStudentEntity(student);
    String newStudentId = student.getId();
    for (CourseEnrollmentEntity newEnrollment : enrollment) {
      newEnrollment.setStudentId(newStudentId);
      repository.registerCourseEnrollment(newEnrollment);
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


}
