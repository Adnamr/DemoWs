package com.demows.lbvie.exceptions;

public class InternalServerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public InternalServerException() {
		super();
	}

	public InternalServerException(String message) {
		super(message);
	}

}
