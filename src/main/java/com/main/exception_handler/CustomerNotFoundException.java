package com.main.exception_handler;

public class CustomerNotFoundException extends RuntimeException {

	/**
	 * @author Amol Suryavanshi
	 */ 
	
	private static final long serialVersionUID = 7948988832252888146L;

	public CustomerNotFoundException(String message){
		super(message);
	}

}
