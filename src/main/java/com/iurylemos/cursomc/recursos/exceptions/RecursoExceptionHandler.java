package com.iurylemos.cursomc.recursos.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.iurylemos.cursomc.servicos.exceptions.AuthorizationException;
import com.iurylemos.cursomc.servicos.exceptions.DataIntegrityException;
import com.iurylemos.cursomc.servicos.exceptions.FileException;
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
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<ErroPadrao> dataintegrity(DataIntegrityException e, HttpServletRequest request) {
		/*
		 * Isso aqui é uma classe auxiliar que vai interceptar
		 * as excessoes e ela obrigatoriamente dentro do framework
		 * tem que ter essa excessao
		 * No 1º parametro vai receber as excessoes que estourou.
		 * No 2º parametro as informações das requisições.
		 */
		//Isso é padrão do controllerAdvace
		//No lugar do NOT_FOUND vai ser o BAD_REQUEST
		
		//Se estourar a excessão vai aparecer o meu StandardError.
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	//Metodo de erro quando inserimos um campo vázio, ou fora da regra.
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
	
		//Vou querer gerar uma lista de erros com as mensagens de erro.
		
		ValidacaoError erro = new ValidacaoError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		//Antes de retornar, eu tenho que pecorrer a lista de erros que já tem na excessão
		//Padrão do FRAMEWORK pegando só o nome do campo e a mensagem corespondente ao erro.
		
		//Na assinatura do metodo tem o objeto e, que no caso é a excessão que vai dar lá
		//E nele tem os get que são
		//e.getBindingResult().getFieldError() com isso daqui eu acesso todos os erros
		//De campos que aconteceram na excessao MethodArgumentNotValidException.
		//Cada erro é do tipo FieldError, vou chamar ele de x
		//Na assinatura estou dizendo
		//Para cada FieldError x nessa lista de e.
		//No meu objeto erro vou chamar o add error pegando o nome do campo e a mensagem
		
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			erro.addError(x.getField(), x.getDefaultMessage());
		}
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	/*
	 *  Copiei o objetoNotFountException
	 *  E para essa excessão que eu criei ser válida
	 *  tenho que criar aqui dentro do Handler
	 *  
	 *  Coloquei nas ordens da aula, como essa foi a ultima botei aqui.
	 * 
	 * 
	 * 
	 */
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ErroPadrao> authorization(AuthorizationException e, HttpServletRequest request) {
	
		//Como é o erro do acesso negado ai no lugar do NOT_FOUND, boto o FORBIDDEN
		ErroPadrao erro = new ErroPadrao(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<ErroPadrao> file(FileException e, HttpServletRequest request) {
	
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	//Excessão da ServiceAmazon
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<ErroPadrao> amazonService(AmazonServiceException e, HttpServletRequest request) {
		/*
		 * O status HTTP vai ser o que vier na excessão da Amazon
		 * Pois na Amazon ela retorna um código de erro HTTP
		 */
		//Pega o código HTTP da Amazon e transforma para HttpStatus
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		ErroPadrao erro = new ErroPadrao(code.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(code).body(erro);
	}
	
	/**
	 * AmazonClient Exception
	 * que é a excessão mais comum da Amazon
	 */
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<ErroPadrao> amazonClient(AmazonClientException e, HttpServletRequest request) {
	
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	//Erro do AmazonS3Exception
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<ErroPadrao> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
	
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
}
