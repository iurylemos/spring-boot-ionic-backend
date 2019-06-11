package com.iurylemos.cursomc.dto;

import java.io.Serializable;

public class CredenciaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Credênciais DTO é um objeto para receber a requisição com email e senha.
	 */
	
	private String email;
	private String senha;
	
	public CredenciaisDTO() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
