package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;

@Mapper
public interface Repository {

  @Select("SELECT * FROM students WHERE was_deleted = false")
  List<Student> searchStudent();

  @Select("SELECT * FROM student_courses")
  List<Course> searchCourse();

  @Insert("INSERT INTO students (name, kana_name, nickname, email_address, residence, age, gender, remark, was_deleted)"
      + " VALUES (#{name}, #{kanaName}, #{nickname}, #{emailAddress}, #{residence}, #{age}, #{gender}, #{remark}, false)")
  //Switched id column to generate keys with AUTO_INCREMENT
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentEntity(Student student);

  @Insert("INSERT INTO student_courses (student_id, course_name, course_start_at, course_end_at) "
      + "VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerCourseEnrollment (Course course);

//  new @SELECT to pull from DB by ID link
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student fetchById(String id);

//  new UPDATE to update new information through updateStudent
//  !!! """ !!!! to help writing multiple lines of text without opening and closing all the time - apparently not :D
  @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email_address = #{emailAddress}, "
      + "residence = #{residence}, age = #{age}, gender = #{gender}, remark = #{remark}, was_deleted = #{wasDeleted}"
      //add a space...fml
      + " WHERE id = #{id}")
  void updateStudentEntity(Student student);

  @Update("UPDATE student_courses SET course_name = #{courseName}  WHERE id = #{id}")
  void updateCourseEnrollment (Course course);




}
