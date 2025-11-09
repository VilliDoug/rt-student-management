package raisetech.student.management.controller.handler.exception;

public class StudentNotFoundEx extends RuntimeException {

  public StudentNotFoundEx(String message) {
    super(message);
  }
}
