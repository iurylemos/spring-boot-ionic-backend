package com.iurylemos.cursomc.recursos.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.iurylemos.cursomc.servicos.exceptions.ObjetoNotFountException;

@ControllerAdvice
public class RecursoExceptionHandler {
	//Manipulador de excessões do meu recurso.
	//Gerando o erro com o JSON e mensagem de erro
	//Com código HTTP e que instante que ocorreu o erro
	
	//Vou criar um objeto auxiliar aqui no mesmo pacote.
	
	//Metodo o do objeto que vai conter o erro
	
	//Indicar que aqui é um tratador de excessões
	//Desse tipo de excessão que é da classe ObjetoNaoEncontrado.
	@ExceptionHandler(ObjetoNotFountException.class)
	public ResponseEntity<ErroPadrao> objetoNotFount(ObjetoNotFountException e, HttpServletRequest request) {
		/*
		 * Isso aqui é uma classe auxiliar que vai interceptar
		 * as excessoes e ela obrigatoriamente dentro do framework
		 * tem que ter essa excessao
		 * No 1º parametro vai receber as excessoes que estourou.
		 * No 2º parametro as informações das requisições.
		 */
		//Isso é padrão do controllerAdvace
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
}
