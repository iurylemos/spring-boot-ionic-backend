package com.iurylemos.cursomc.recursos;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.dto.CategoriaDTO;
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
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		//Botei categoria no lugar de ?, por que ou retorna categoria ou Excesao.
		Categoria obj = servico.find(id);
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

	@PreAuthorize("hasAnyRole('ADMIN')") //Só quem pode inserir são os ADMIN
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		//Esse metodo vai chamar um serviço que insere essa categoria no BD
		//Coloquei obj recebendo pois na operação save ela me retorna um OBJETO.
		//Modifiquei para DTO, e para validar aqui abaixo tenho que mudar no meu servico
		//Para a validação do meu DTO ser valida preciso colocar a anotação Valid
		//Botei para DTO pois fiz uma validação lá, com os nomes.
		//Na classe de servico fiz um metodo que recebe uma categoriaDTO e retorna uma CATEGORIA
		
		Categoria obj = servico.fromDTO(objDto);
		
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
	
	//Metodo para atualização.
	//Esse metodo vai ter o id pois é apartir desse id que eu atualizo o campo.
	//Vai ser uma mistura do GET com o POST.
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
		//Garantir que a categoria que vai ser atualizada é a que eu passar o código na URL
		//Criei um metodo que transforma uma Categoria em CategoriaDTO
		//Que contem as validação.
		Categoria obj = servico.fromDTO(objDto);
		obj.setId(id);
		obj = servico.update(obj);
		//conteudo vázio = noContent.
		return ResponseEntity.noContent().build();
	}
	
	//Metodo deletar.
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		servico.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//Metodo de mostrar todas categorias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = servico.findAll();
		//Converter essa lista de categorias, para CategoriaDTO.
		//A melhor forma é ir no DTO e criar um construtor que receba o objeto correspondete
		//Lá das entidades de domínio.
		//Utilizar o Strem para pecorrer a lista de categorias
		//O map efetuar uma operação para cada elemento da lista
		//Vou chamar ela de obj e para cada elemento da minha lista
		// -> aeroFunction = FUNÇÃO anonima
		//instaciar a minha categoriaDTO passando o obj como parametro
		//E depois converter para lista de novo com o collect.
		
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
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
	
	//Passo 1 do caso de uso!
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPorPage", defaultValue="24") Integer linesPorPage, 
			@RequestParam(value="orderBy", defaultValue="nome" ) String orderBy, 
			@RequestParam(value="direcao", defaultValue="ASC" ) String direcao) {
		Page<Categoria> list = servico.findPage(page, linesPorPage, orderBy, direcao);
		/*
		 * Como o PAGE ele é JAVA 8 compliance, ele não vai precisar nem do STREAM
		 * E nem converter para lista novamente.
		 */
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listDto);
	}
	
}
