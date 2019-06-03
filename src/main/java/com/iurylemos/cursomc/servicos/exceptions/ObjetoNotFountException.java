package com.iurylemos.cursomc.servicos.exceptions;

public class ObjetoNotFountException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ObjetoNotFountException(String msg) {
		super(msg);
	}
	//O outro contrustor é sobre a excesao e a causa.
	public ObjetoNotFountException(String msg, Throwable causa) {
		super(msg, causa);
	}

}
