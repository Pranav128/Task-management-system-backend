package com.app.exception;

import com.app.exception.custom.TaskException;
import com.app.payload.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class TaskExceptionHandler {

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<ExceptionResponse> taskException(TaskException ex){
        ExceptionResponse response=new ExceptionResponse();
        response.setException(ex.toString());
        response.setMessage(ex.getMessage());
        response.setStatusCode(0);
        return new  ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exception(Exception ex){
        ExceptionResponse response=new ExceptionResponse();
        response.setException(ex.toString());
        response.setMessage(ex.getMessage());
        response.setStatusCode(0);
        return new  ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
