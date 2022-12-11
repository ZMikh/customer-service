package ru.mikhailova.customerService.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ClaimAnswerExceptionHandler extends Throwable {
    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<String> claimAnswerNotFound(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There's no answer to claim");
    }
}
