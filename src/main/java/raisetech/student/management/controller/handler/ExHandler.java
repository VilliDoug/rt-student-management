package raisetech.student.management.controller.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationEx(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>(); //starting with a map right away, no need for a list.
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())); // this is the change - it will display where was wrong and its specific message.
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
}
//I like this one more, so I will keep it and chuck the old one away

//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public ResponseEntity<Map<String, List<String>>> handleValidationEx(MethodArgumentNotValidException ex) {
//    List<String> errorResponse = ex.getBindingResult().getAllErrors().stream()
//        .map(error -> error.getDefaultMessage())
//        .collect(Collectors.toList());
//
//    Map<String, List<String>> errorResponseMap = new HashMap<>();
//    errorResponseMap.put("バリデーションエラー", errorResponse);
//
//    return ResponseEntity
//        .status(HttpStatus.BAD_REQUEST)
//        .body(errorResponseMap);
//  }

}


