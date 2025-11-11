package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sun.tools.javac.Main;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
// 事前準備
    List<Student> studentList = new ArrayList<>();
    List<Course> courseList = new ArrayList<>();
    when(repository.searchAllStudents()).thenReturn(studentList);
    when(repository.searchAllCourses()).thenReturn(courseList);
//    実行
    List<StudentDetail> actual = sut.searchStudentList();
//    検証
    verify(repository, times(1)).searchAllStudents();
    verify(repository, times(1)).searchAllCourses();
    verify(converter, times(1)).convertDetails(studentList, courseList);

//    後処理
//    ここでDBをもとに戻す。

// 課題・Service処理のテストを書いて動作　init not needed because private
  }

}