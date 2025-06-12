package com.project.WeatherApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationFailHandler(MethodArgumentNotValidException e){

        Map<String, String> errorMap = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(er -> {
            errorMap.put(er.getField(), er.getDefaultMessage());
        });

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
