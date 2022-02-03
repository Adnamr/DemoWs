package com.demows.lbvie.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.demows.lbvie.exceptions.res.ExceptionResponse;

@ControllerAdvice
public class GlobalController {
	
    private static final Logger log = LoggerFactory.getLogger(GlobalController.class);
	
	@ExceptionHandler(NoDataFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleNodataFoundException(
        NoDataFoundException ex, HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(ex.getMessage());
		error.callerURL(request.getRequestURI());

		return new ResponseEntity<ExceptionResponse>(error,HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleInternalServerException(
    		InternalServerException ex, HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(ex.getMessage());
		error.callerURL(request.getRequestURI());

		return new ResponseEntity<ExceptionResponse>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
//	@ExceptionHandler(EntityException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//    private ResponseEntity<ExceptionResponse> handleEntityNotFound(EntityException entityex,MethodArgumentNotValidException  ex,HttpServletRequest request){
//		
//		List<String> details = new ArrayList<>();
//        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
//            details.add(error.getDefaultMessage());
//        }
//        
//		ExceptionResponse error = new ExceptionResponse();
//		error.setErrorMessage(entityex.getMessage());
//		error.callerURL(request.getRequestURI());
//		error.setDetails(details);
//
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

	@ExceptionHandler({ ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException ex, HttpServletRequest request) {
	    List<String> errors = new ArrayList<String>();
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
	    }

	    ExceptionResponse error = new ExceptionResponse();
	    error.setErrorMessage("Error saving data");
		error.callerURL(request.getRequestURI());
        error.setDetails(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleConstraintViolation(
			MethodArgumentNotValidException ex, HttpServletRequest request) {
	    List<String> errors = new ArrayList<String>();
	    List<ObjectError> allErrors = ex.getAllErrors();
	    for (ObjectError violation : allErrors) {
	        errors.add(ex.getFieldError().getField() + ": " + violation.getDefaultMessage());
	    }

	    ExceptionResponse error = new ExceptionResponse();
	    error.setErrorMessage("Error saving data");
		error.callerURL(request.getRequestURI());
        error.setDetails(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
//        log.error("404 situation detected.",ex);
//        return "Specified path not found on this server";
//    }
	
	@ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HashMap<String, String> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", e.getLocalizedMessage());
        return response;
    }
}
