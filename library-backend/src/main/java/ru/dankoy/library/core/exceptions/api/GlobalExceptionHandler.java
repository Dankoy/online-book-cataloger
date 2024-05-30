package ru.dankoy.library.core.exceptions.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.dankoy.library.core.exceptions.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({EntityNotFoundException.class})
  public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {

    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());

    ApiError err =
        new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Entity not found",
            details);

    return ResponseEntityBuilder.build(err);

  }


  @Override
  public ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {

    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());

    ApiError err =
        new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Message not readable", details);

    return ResponseEntityBuilder.build(err);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {

    List<String> details =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
            .toList();

    ApiError err =
        new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Validation Errors", details);

    return ResponseEntityBuilder.build(err);
  }
}
