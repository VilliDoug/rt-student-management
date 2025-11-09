package raisetech.student.management.controller.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.student.management.controller.handler.reponses.ValidationErrorResponse;

@RestControllerAdvice
public class ExHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationEx(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>(); //starting with a map right away, no need for a list.
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())); // this is the change - it will display where was wrong and its specific message.
    ValidationErrorResponse response = new ValidationErrorResponse(errors);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
