package com.iurylemos.cursomc.servicos.exceptions;

public class DataIntegrityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}
	//O outro contrustor é sobre a excesao e a causa.
	public DataIntegrityException(String msg, Throwable causa) {
		super(msg, causa);
	}

}
