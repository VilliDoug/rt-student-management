package raisetech.student.management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.MainConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.Repository;

/**
 * 受講生情報を取り扱うServiceです。
 * 受講生の検索や登録、更新処理を行います。
 */
@Service
public class MainService {

  private Repository repository;

  @Autowired
  private MainConverter converter;

  @Autowired
  public MainService(Repository repository, MainConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生の一覧検索を行います。
   * 全件検索を行うので、条件指定は行わないません。
   *
   * @return　受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchStudent();
    List<Course> courseList = repository.searchCourseList();
    return converter.convertStudentDetails(studentList, courseList);  }

  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  public StudentDetail searchStudentId(String id) {
    Student student = repository.fetchById(id);
    List<Course> courseList = repository.searchCourseById(student.getId());
    return new StudentDetail(student, courseList);
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.registerStudentEntity(studentDetail.getStudent());
    for (Course course : studentDetail.getStudentCourses()) {
      course.setStudentId(studentDetail.getStudent().getId());
      course.setCourseStartAt(String.valueOf(LocalDate.now()));
      repository.registerCourseEnrollment(course);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudentDetails (StudentDetail studentDetail) {
    repository.updateStudentEntity(studentDetail.getStudent());
  }

}
