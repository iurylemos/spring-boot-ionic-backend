package com.iurylemos.cursomc.recursos.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Classe CampoMessagem.
	//Atributos com nome do campo e a messagem de erro.
	
	private String campoNome;
	private String message;
	
	public FieldMessage() {
		
	}

	public FieldMessage(String campoNome, String message) {
		super();
		this.campoNome = campoNome;
		this.message = message;
	}

	public String getCampoNome() {
		return campoNome;
	}

	public void setCampoNome(String campoNome) {
		this.campoNome = campoNome;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
