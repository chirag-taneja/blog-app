package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvisor {

    @ExceptionHandler
    public ResponseEntity<ResourseNotFound> handleExcepiton(Exception e)
    {
        ResourseNotFound resourseNotFound=new ResourseNotFound(e.getMessage(),System.currentTimeMillis(), HttpStatus.NOT_FOUND.toString());
        return  new ResponseEntity<>(resourseNotFound,HttpStatus.NOT_FOUND);
    }
}
