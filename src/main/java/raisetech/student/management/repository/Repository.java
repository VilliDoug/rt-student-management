package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface Repository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return　受講生一覧（全件）
   */
  @Select("SELECT * FROM students")
  List<Student> searchStudents();

  /**
   * 受講生IDに紐づく受講生検索を行います。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student fetchById(String id);

  /**
   * 受講生の削除フラッグに関した検索を行います。
   *
   * @return　受講生（削除無し）
   */
  @Select("SELECT * FROM students WHERE was_deleted = false")
  List<Student> searchStudent();

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return　受講生コース情報一覧（全件）
   */
  @Select("SELECT * FROM student_courses")
  List<Course> searchCourseList();

  @Select("SELECT * FROM student_courses WHERE student_id = #{studentId}")
  List<Course> searchCourseById(String id);

  @Insert("INSERT INTO students (name, kana_name, nickname, email_address, residence, age, gender, remark, was_deleted)"
      + " VALUES (#{name}, #{kanaName}, #{nickname}, #{emailAddress}, #{residence}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentEntity(Student student);

  @Insert("INSERT INTO student_courses (student_id, course_name, course_start_at, course_end_at) "
      + "VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerCourseEnrollment (Course course);

  @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email_address = #{emailAddress}, "
      + "residence = #{residence}, age = #{age}, gender = #{gender}, remark = #{remark}, was_deleted = #{wasDeleted}"
      + " WHERE id = #{id}")
  void updateStudentEntity(Student student);

  @Update("UPDATE student_courses SET course_name = #{courseName}  WHERE id = #{id}")
  void updateCourseEnrollment (Course course);




}
