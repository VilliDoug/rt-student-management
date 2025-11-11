package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.MainConverter;
import raisetech.student.management.data.Course;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.Repository;


@ExtendWith(MockitoExtension.class)
class MainServiceTest {

  @Mock
  private Repository repository;

  @Mock
  private MainConverter converter;

  private MainService sut;

  @BeforeEach
  void before() {
    sut = new MainService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出されていること() {
    List<Student> studentList = new ArrayList<>();
    List<Course> courseList = new ArrayList<>();
    List<StudentDetail> expectedDetails = new ArrayList<>();

    when(repository.searchAllStudents()).thenReturn(studentList);
    when(repository.searchAllCourses()).thenReturn(courseList);
    when(converter.convertDetails(studentList, courseList)).thenReturn(expectedDetails);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository, times(1)).searchAllStudents();
    verify(repository, times(1)).searchAllCourses();
    verify(converter, times(1)).convertDetails(studentList, courseList);

    assertEquals(expectedDetails, actual);

  }


  @Test
  void 受講生詳細の検索_リポジトリからIDに紐づく検索処理が適切に呼び出されていること() {
    Student student = new Student();
    student.setId("555");
    List<Course> courseList = new ArrayList<>();
    when(repository.fetchById(student.getId())).thenReturn(student);
    when(repository.fetchCourseById(student.getId())).thenReturn(courseList);

    StudentDetail actual = sut.searchStudentId(student.getId());

    verify(repository, times(1)).fetchById(student.getId());
    verify(repository, times(1)).fetchCourseById(student.getId());

    assertEquals(student, actual.getStudent());
    assertEquals(courseList, actual.getCourseList());
  }

  @Test
  void 受講生詳細の登録が適切に実装していること() {
    Student student = new Student();
    student.setId("555");
    student.setName("Gordon");

    Course course = new Course();
    course.setCourseName("Testing Unit Course");
    List<Course> courseList = List.of(course);

    StudentDetail expectedRegisterDetail = new StudentDetail(student, courseList);

    StudentDetail actual = sut.registerStudent(expectedRegisterDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerCourse(course);

    assertEquals(student, actual.getStudent());
    assertEquals(student.getId(), actual.getCourseList().get(0).getStudentId());
    assertEquals(expectedRegisterDetail, actual);

  }

  @Test
  void 受講生詳細の更新処理が適切に実装していること() {
    Student student = new Student();
    student.setId("777");
    Course course = new Course();
    course.setCourseName("Slot Mechanic");
    List<Course> courseList = List.of(course);
    StudentDetail expectedUpdateDetail = new StudentDetail(student, courseList);

    sut.updateStudent(expectedUpdateDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateCourseName(course);
  }

}