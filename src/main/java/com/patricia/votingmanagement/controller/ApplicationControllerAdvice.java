package com.patricia.votingmanagement.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.patricia.votingmanagement.exception.InvalidRequestException;
import com.patricia.votingmanagement.exception.NotAuthorizedException;
import com.patricia.votingmanagement.exception.NotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(InvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleException(InvalidRequestException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFoundException(NotFoundException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(InterruptedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String timerTaskInterruptedException(InterruptedException exception) {
		return "Error during vote session. Session aborted.";
	}
	
	
	@ExceptionHandler(NotAuthorizedException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public String timerTaskInterruptedException(NotAuthorizedException exception) {
		return exception.getMessage();
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .reduce("", (acc, error) -> acc + error + "\n");
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(error -> error.getPropertyPath() + " " + error.getMessage())
                .reduce("", (acc, error) -> acc + error + "\n");
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        if (Objects.nonNull(ex) && Objects.nonNull(ex.getRequiredType())) {
            String type = ex.getRequiredType().getName();
            String[] typeParts = type.split("\\.");
            String typeName = typeParts[typeParts.length - 1];
            return ex.getName() + " should be of type " + typeName;
        }
        return "Argument type not valid";
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class) 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    	return ex.getMessage();
    }
    
    
}
