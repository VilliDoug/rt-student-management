package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生情報を扱うリポジトリ。
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   *
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> searchStudents();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchCourses();

 // @Insert("INSERT INTO students (name, age) VALUES(#{name}, #{age})")
 // void registerStudent(String name, int age);

 // @Update("UPDATE students SET age = #{age} WHERE name = #{name}")
 // void updateStudent(String name, int age);

  //@Delete("DELETE FROM students WHERE name= #{name}")
  //void deleteStudent(String name);

}
