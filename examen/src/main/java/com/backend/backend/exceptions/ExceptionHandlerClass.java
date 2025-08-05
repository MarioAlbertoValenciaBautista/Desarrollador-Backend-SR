package com.backend.backend.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass {

    // This class can be used to handle exceptions globally in the application
    // You can define methods here to handle specific exceptions and return custom responses
    // For example, you can use @ExceptionHandler annotations to catch specific exceptions
    // and return a custom error response.

    // Example:
    // @ExceptionHandler(IllegalArgumentException.class)
    // public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    //     return ResponseEntity.badRequest().body(ex.getMessage());
    // }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An error occurred with this message: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("A runtime error occurred with this message: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
