package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.ApplicationStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface MainRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return　受講生一覧（全件）
   */
  List<Student> searchAllStudents();

  /**
   * 受講生IDに紐づく受講生検索を行います。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  Student fetchById(String id);

  /**
   * 受講生の削除フラッグに関した検索を行います。
   *
   * @return　受講生（削除無し）
   */
  List<Student> searchNotDeletedStudent();

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return　受講生コース情報一覧（全件）
   */
  List<Course> searchAllCourses();

  List<Course> fetchCourseById(String studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student　受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   *
   * @param course　受講生コース情報
   */
  void registerCourse(Course course);

  /**
   * 受講生を更新します。
   *
   * @param student　受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param course　受講生コース情報
   */
  void updateCourseName(Course course);

  /**
   * 申込状況の全件検索
   *
   * @return
   */
  List<ApplicationStatus> searchAllStatus();

  /**
   * 申込状況をコースIDに紐づく申込状況検索を行います。
   *
   * @return　申込状況
   */
  List<ApplicationStatus> fetchStatusByCourseIds(List<String> courseId);

  void registerStatus(ApplicationStatus applicationStatus);

  void updateStatus(ApplicationStatus applicationStatus);


}
