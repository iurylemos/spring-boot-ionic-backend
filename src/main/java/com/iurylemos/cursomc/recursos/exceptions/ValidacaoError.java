package com.iurylemos.cursomc.recursos.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoError extends ErroPadrao {
	private static final long serialVersionUID = 1L;
	//Classe de erro herdando da classe ErroPadrao
	//Só que agora acrescentando a lista do FieldMessage.
	//Vou modificar lá no RecursoE..Handler
	//Erro específico para validação de formulário.
	
	//Lista de erros que tem fieldName e mensagem.
	//Ele tem tudo que o ErroPadrao tbm e uma lista de erros
	private List<FieldMessage> erros = new ArrayList<>();

	/*
	 * Fica até errado se eu colocar essa lista no contrutor
	 * Por que já instanciei ela vázio aqui acima.
	 */
	
	public ValidacaoError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return erros;
	}

	//Adicionando erro.
	
	public void addError(String campoNome, String message) {
		erros.add(new FieldMessage(campoNome, message));
	}
	
	
	
	

	/* Não quero acrescentar uma lista de erros de uma vez. 
	public void setList(List<FieldMessage> list) {
		this.list = list;
	} */

	
	

}
