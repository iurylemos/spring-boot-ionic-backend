package com.iurylemos.cursomc.recursos.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoError extends ErroPadrao {
	private static final long serialVersionUID = 1L;
	//Classe de erro herdando da classe ErroPadrao
	//Só que agora acrescentando a lista do FieldMessage.
	//Vou modificar lá no RecursoE..Handler
	
	private List<FieldMessage> erros = new ArrayList<>();
	
	
	public ValidacaoError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}

	//Tirando o getList e colocando getErrors
	//por que no java o que importa é o que está no GET
	//No JSON quando for executado vai aparecer Errors:
	//E não list: ...
	
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
