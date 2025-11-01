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

  @Insert("INSERT INTO students (name) VALUES (#{name})")
  //Switched id column to generate keys with AUTO_INCREMENT
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentEntity(StudentEntity student);

  @Insert("INSERT INTO student_courses (student_id, course_name) VALUES (#{studentId}, #{courseName})")
  void registerCourseEnrollment (CourseEnrollmentEntity course);

}
