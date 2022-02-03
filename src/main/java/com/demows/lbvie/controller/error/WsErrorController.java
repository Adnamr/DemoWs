package com.demows.lbvie.controller.error;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demows.lbvie.exceptions.res.ErrorResponse;
import com.demows.lbvie.exceptions.res.ExceptionResponse;

@RestController
public class WsErrorController implements ErrorController {
	
    private static final Logger log = LoggerFactory.getLogger(WsErrorController.class);
    
	@RequestMapping("/error")
	public ResponseEntity<Object> error(HttpServletRequest request) {
		
//	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//	    
//		Integer statusCode = Integer.valueOf(status.toString());
//		
//		log.info("STATUS :::"+HttpStatus.NOT_FOUND.value());
//
//		if (statusCode == HttpStatus.NOT_FOUND.value()) {
//			Error404(request);
//		} else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
//			Error401(request);
//		}
//		return null;

		ErrorResponse error = new ErrorResponse();
	    error.setMessage("URL not found");
		error.setCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}
	
	private ResponseEntity<Object> Error404(HttpServletRequest request)
	{
		ErrorResponse error = new ErrorResponse();
	    error.setMessage("URL not found");
		error.setCode(404);
		//error.setUrl(request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	private ResponseEntity<Object> Error401(HttpServletRequest request)
	{
		ExceptionResponse error = new ExceptionResponse();
	    error.setErrorMessage("URL not Authorized");
		error.callerURL(request.getRequestURI());
		List<String> details = new ArrayList<String>();
		details.add(""+HttpStatus.UNAUTHORIZED);
		error.setDetails(details);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	

}
