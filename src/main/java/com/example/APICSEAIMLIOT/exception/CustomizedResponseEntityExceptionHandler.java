package com.example.APICSEAIMLIOT.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
	    // This method handles all exceptions of type Exception and its subclasses.
	    // It is marked as `final` to prevent any further subclassing.

	    // Create an ErrorDetails object containing timestamp, error message, and request details.
	    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

	    // Return a ResponseEntity with the error details and a status code of INTERNAL_SERVER_ERROR (500).
	    return new ResponseEntity<Object>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CourseNotFoundException.class)
	public final ResponseEntity<Object> handleCourseNotFoundException(Exception ex, WebRequest request) throws Exception {
	    // This method handles exceptions of type CourseNotFoundException specifically.

	    // Create an ErrorDetails object containing timestamp, error message, and request details.
	    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

	    // Return a ResponseEntity with the error details and a status code of NOT_FOUND (404).
	    return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	//FOR HANDLING ERROR MESSAGE IN TIMES OF VALIDATION
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		StringBuffer errorMessage = new StringBuffer();
		
		for (ObjectError i : ex.getAllErrors()) {
			errorMessage.append(i.getDefaultMessage() + " ");			
		}
		
	    // Create an ErrorDetails object containing timestamp, error message, and request details.
	    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errorMessage.toString(), request.getDescription(false));

	    // Return a ResponseEntity with the error details and a status code of BAD_REQUEST (400).
	    return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	

}
