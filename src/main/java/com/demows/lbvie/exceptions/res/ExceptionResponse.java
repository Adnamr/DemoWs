package com.demows.lbvie.exceptions.res;

import java.util.List;

public class ExceptionResponse {
	
	private String errorMessage;
	
	private String requestedURI;
	
	private List<String> details;
	
	

	public ExceptionResponse() {
		super();
	}

	public ExceptionResponse(String errorMessage, String requestedURI,List<String> details) {
		super();
		this.errorMessage = errorMessage;
		this.requestedURI = requestedURI;
		this.details      = details;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRequestedURI() {
		return requestedURI;
	}

	public void callerURL(final String requestedURI) {
		this.requestedURI = requestedURI;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}
	

}
