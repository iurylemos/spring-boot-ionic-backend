package com.iurylemos.cursomc.servicos.exceptions;

public class FileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileException(String msg) {
		super(msg);
	}
	//O outro contrustor é sobre a excesao e a causa.
	public FileException(String msg, Throwable causa) {
		super(msg, causa);
	}

}
