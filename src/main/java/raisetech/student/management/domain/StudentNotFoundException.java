package raisetech.student.management.domain;

public class StudentNotFoundException extends RuntimeException{
  public StudentNotFoundException (String message) {
    super(message);
  }

}
