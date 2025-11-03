package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<StudentEntity> searchStudents();

  @Select("SELECT * FROM student_courses")
  List<CourseEnrollmentEntity> searchCourses();

  @Insert("INSERT INTO students (name, kana_name, nickname, email_address, region, age, gender, remark, was_deleted)"
      + " VALUES (#{name}, #{kanaName}, #{nickname}, #{emailAddress}, #{region}, #{age}, #{gender}, #{remark}, false)")
  //Switched id column to generate keys with AUTO_INCREMENT
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentEntity(StudentEntity student);

  @Insert("INSERT INTO student_courses (student_id, course_name, course_start_at, course_end_at) "
      + "VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")

  void registerCourseEnrollment (CourseEnrollmentEntity courseEnrollmentEntity);

}
