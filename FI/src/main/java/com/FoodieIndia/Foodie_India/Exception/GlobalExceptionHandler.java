package com.FoodieIndia.Foodie_India.Exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRecepieDetailsException.class)
    public ResponseEntity<?> handleInvalidRecepieDetailsException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecepiesNotFoundException.class)
    public ResponseEntity<?> handleRecepieNotException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<?> handlePlanNotfoundException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPlanDetailsException.class)
    public ResponseEntity<?> handleInvalidPlanException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<?> handleSubscriptionNotFoundException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSubscritptionDetailsException.class)
    public ResponseEntity<?> handleInvalidSubscritpionException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleInvalidUserDetailsException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserDetailsException.class)
    public ResponseEntity<?> handleUserDetailsException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("Status", HttpStatus.BAD_REQUEST);
        body.put("stackTrace", Arrays.stream(ex.getStackTrace())
                .limit(2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
