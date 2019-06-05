package com.iurylemos.cursomc.recursos;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.servicos.CategoriaServico;

//Anotação para Rest

//Anotação para Request
//No valor nome do ENDPOINT rest que vou querer para o recurso
//O pessoal costuma usar o nome do conceito no plural.
//De acordo como é utilizado no mercado.
@RestController
@RequestMapping(value="/categorias")
public class CategoriaRecurso {
	
	
	//Depedência
	@Autowired
	private CategoriaServico servico;
	
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
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Categoria obj = servico.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//Operação para inserir uma nova categoria.
	//Da mesma forma que fizemos na função acima find para
	//efetuar a operação de GET passando o ID e retornando CATEGORIA
	//Vamos criar um metodo para receber uma categoria no formato JSON
	//E inserir essa categoria no Banco de Dados.
	//Void significa que não vai ter corpo.
	//Quando eu inserir com sucesso vou retornar uma resposta com corpo vázio.
	//Só que vou utilizar o POST.
	//Para que esse objeto seja construido a partir do JSON que eu enviar
	//Tenho que colocar do lado lá do parametro o @RequestBody
	//Isso faz com oque o JSON seja convertido para objeto JAVA automaticamente
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		//Esse metodo vai chamar um serviço que insere essa categoria no BD
		//Coloquei obj recebendo pois na operação save ela me retorna um OBJETO.
		
		obj = servico.insert(obj);
		
		//Fornecendo o ID como novo na nova URI. ou seja URL.
		//tipo quando eu colocar /categorias/3, vai ter o novo id criado associado lá.
		//Esse metodo fromCurrentRequest pega o localhost:8080/categorias.
		//Para atribuir o valro do objeto eu utilizo o buildAndExpand
		//E para finalizar converto ele para uri utilizando o toUri
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		//created gera o código 201 que recebe a URI como argumento
		//e o build para gerar a resposta bonita para mim.
		
		return ResponseEntity.created(uri).build();
	}
}
