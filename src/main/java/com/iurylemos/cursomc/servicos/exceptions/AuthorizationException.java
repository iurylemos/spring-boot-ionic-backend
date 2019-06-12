package com.iurylemos.cursomc.servicos.exceptions;

public class AuthorizationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String msg) {
		super(msg);
	}
	//O outro contrustor é sobre a excesao e a causa.
	public AuthorizationException(String msg, Throwable causa) {
		super(msg, causa);
	}

}
