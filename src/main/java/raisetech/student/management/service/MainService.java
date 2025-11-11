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
   * 受講生詳細の一覧検索を行います。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchAllStudents();
    List<Course> courseList = repository.searchAllCourses();
    return converter.convertDetails(studentList, courseList);  }

  /**
   * 受講生詳細検索です。
   * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id　受講生ID
   * @return　受講生詳細
   */
  public StudentDetail searchStudentId(String id) {
    Student student = repository.fetchById(id);
    List<Course> courseList = repository.fetchCourseById(student.getId());
    return new StudentDetail(student, courseList);
  }

  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値や日付情報（コース開始日）を設定します。
   *
   * @param studentDetail　受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    studentDetail.getCourseList().forEach(course -> {
      initStudentCourse(course, student);
      repository.registerCourse(course);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する
   *
   * @param course　受講生コース情報
   * @param student　受講生
   */
  private void initStudentCourse(Course course, Student student) {
    course.setStudentId(student.getId());
    course.setCourseStartAt(String.valueOf(LocalDate.now()));
  }

  /**
   * 受講生詳細の更新を行います。受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail　受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getCourseList()
        .forEach(course -> repository.updateCourseName(course));
  }

}
