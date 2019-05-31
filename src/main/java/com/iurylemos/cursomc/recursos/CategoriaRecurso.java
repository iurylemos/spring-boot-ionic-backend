package com.iurylemos.cursomc.recursos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Anotação para Rest

//Anotação para Request
//No valor nome do ENDPOINT rest que vou querer para o recurso
//O pessoal costuma usar o nome do conceito no plural.
//De acordo como é utilizado no mercado.
@RestController
@RequestMapping(value="/categorias")
public class CategoriaRecurso {
	
	//Para ser REST preciso associar com o HTTP
	/*
	 * No padrão REST é muito importante que seja atribuido
	 * os verbos HTTP adequados para cada operação.
	 * 
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String listar() {
		return "REST está funcionando";
	}
}
