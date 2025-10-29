package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CourseEnrollmentEntity {

  private String id;
  private String studentId;
  private String courseName;
  private String courseStartAt;
  private String courseEndAt;

}


//課題
//受講生コース情報を全権を取ってくること