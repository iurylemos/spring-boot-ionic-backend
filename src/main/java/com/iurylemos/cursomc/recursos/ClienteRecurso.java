package com.iurylemos.cursomc.recursos;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.dto.ClienteDTO;
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
	
	/***
	 * Copiei do ClienteRecurso e vou alterar conforme os dados do cliente.
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		//Garantir que a categoria que vai ser atualizada é a que eu passar o código na URL
		//Criei um metodo que transforma uma Cliente em ClienteDTO
		//Que contem as validação.
		Cliente obj = servico.fromDTO(objDto);
		obj.setId(id);
		obj = servico.update(obj);
		//conteudo vázio = noContent.
		return ResponseEntity.noContent().build();
	}
	
	//Metodo deletar.
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		servico.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//Metodo de mostrar todas categorias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = servico.findAll();
		//Converter essa lista de categorias, para ClienteDTO.
		//A melhor forma é ir no DTO e criar um construtor que receba o objeto correspondete
		//Lá das entidades de domínio.
		//Utilizar o Strem para pecorrer a lista de categorias
		//O map efetuar uma operação para cada elemento da lista
		//Vou chamar ela de obj e para cada elemento da minha lista
		// -> aeroFunction = FUNÇÃO anonima
		//instaciar a minha categoriaDTO passando o obj como parametro
		//E depois converter para lista de novo com o collect.
		
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}
	
	//Metodo para me retornar a paginação.
	//Vou acrescentar o /page para ele retornar todas as paginas.
	//Eu não vou fazer eles como variaveis do Path
	//EX: categorias/page/0/200...
	//Vou fazer como parametro
	//Vai ser assim:
	//categorias/page?page=0linesPorPage=20..
	//coloquei o defaultValue 0 no page, pois se o usuário não escolher ele vai para a 0
	//que é a HOME.
	//já do linesPorPage vou colocar 24
	//Pois o 24 é multiplo de 1,2,3 então fica fácil de organizar o layout de forma
	//responsiva conforme for o tamanho da sua tela.. TIPO de 1 em 1, 2 em 2, 3 em 3..
	
	//valor padrão do orderby se não for informado, vai ser por nome.
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPorPage", defaultValue="24") Integer linesPorPage, 
			@RequestParam(value="orderBy", defaultValue="nome" ) String orderBy, 
			@RequestParam(value="direcao", defaultValue="ASC" ) String direcao) {
		Page<Cliente> list = servico.findPage(page, linesPorPage, orderBy, direcao);
		/*
		 * Como o PAGE ele é JAVA 8 compliance, ele não vai precisar nem do STREAM
		 * E nem converter para lista novamente.
		 */
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(listDto);
	}
}
