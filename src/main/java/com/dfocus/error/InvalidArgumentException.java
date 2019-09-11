package com.dfocus.error;

public class InvalidArgumentException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
    
    public InvalidArgumentException(String message) {
        super(message);
    }
}