package raisetech.student.management.controller.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.student.management.controller.handler.exception.StudentNotFoundEx;
import raisetech.student.management.controller.handler.response.ApiErrorResponse;
import raisetech.student.management.controller.handler.response.ValidationErrorResponse;

@RestControllerAdvice
public class ExHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationEx(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage()));
    ValidationErrorResponse response = new ValidationErrorResponse(errors);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGenericEx(Exception ex) {
    String clientMessage = "内部サーバー エラーが発生しました。";
    ApiErrorResponse response = new ApiErrorResponse(clientMessage);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(StudentNotFoundEx.class)
  public ResponseEntity<ApiErrorResponse> handleNotFoundEx(StudentNotFoundEx ex) {
    String clientMessage = "受講生が見つかりませんでした。";
    ApiErrorResponse response = new ApiErrorResponse(clientMessage);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

}
