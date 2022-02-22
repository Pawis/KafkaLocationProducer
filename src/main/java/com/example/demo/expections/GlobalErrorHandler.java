package com.example.demo.expections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

	    @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
	    	ErrorResponse error = new ErrorResponse();
	    	error.setMessage("You gave an incorrect values..");
	    	error.setStatus(HttpStatus.BAD_REQUEST.value());
	    	
	        return new ResponseEntity<ErrorResponse>(error , HttpStatus.BAD_REQUEST);
	    }
	    
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception) {
	    	ErrorResponse error = new ErrorResponse();
	    	error.setMessage(exception.getBindingResult().getFieldError().getDefaultMessage());
	    	error.setStatus(HttpStatus.BAD_REQUEST.value());
	    	
	        return new ResponseEntity<ErrorResponse>(error , HttpStatus.BAD_REQUEST);
	    }
	
}
