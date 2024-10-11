package org.example.airport.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundHandler(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                                                    HttpStatus.NOT_FOUND.value(),
                                                    LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
