package com.iurylemos.cursomc.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.servicos.ClienteServico;

//Anotação para Rest

//Anotação para Request
//No valor nome do ENDPOINT rest que vou querer para o recurso
//O pessoal costuma usar o nome do conceito no plural.
//De acordo como é utilizado no mercado.
@RestController
@RequestMapping(value="/clientes")
public class ClienteRecurso {
	
	
	//Depedência
	@Autowired
	private ClienteServico servico;
	
	//Para ser REST preciso associar com o HTTP
	/*
	 * No padrão REST é muito importante que seja atribuido
	 * os verbos HTTP adequados para cada operação.
	 * 
	 * quando coloco o value /{id}
	 * ele vai estar depois /categoria que é da classe
	 *  /categoria/id
	 *  
	 *  Para que o SPRING saiba que o ID que veio pela URL
	 *  vai ter que vim para o metodo 
	 *  tenho que colocar a anotação.
	 *  @PathVariable
	 *  
	 *  Retirei o retornar uma lista
	 *  e coloquei o ResponseEntity
	 *  que já encapsula, amarzena varias informações
	 *  de uma resposta HTTP para um serviço REST
	 *  
	 *  Tenho que utilizar o Handler para lançar excessão
	 *  não fica legal eu utilizar o trycatch.
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = servico.find(id);
		return ResponseEntity.ok().body(obj);
	}
}
