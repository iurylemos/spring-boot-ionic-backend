package com.iurylemos.cursomc.recursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iurylemos.cursomc.dominio.Produto;
import com.iurylemos.cursomc.dto.ProdutoDTO;
import com.iurylemos.cursomc.recursos.utils.URL;
import com.iurylemos.cursomc.servicos.ProdutoServico;

//Anotação para Rest

//Anotação para Request
//No valor nome do ENDPOINT rest que vou querer para o recurso
//O pessoal costuma usar o nome do conceito no plural.
//De acordo como é utilizado no mercado.
@RestController
@RequestMapping(value="/produtos")
public class ProdutoRecurso {
	
	
	//Depedência
	@Autowired
	private ProdutoServico servico;
	
	/*
	 * Entre pedido e cliente tem uma associação de mão dupla
	 * Como vou fazer um endpoint para os pedidos
	 * e os pedidos vão mostrar o cliente
	 * eu vou permitir que seja serializado o cliente de um pedido
	 * porem não permitir que seja serializado os pedidos de um cliente
	 * 
	 * isso quer dizer que na pedido vou permitir o clienter ser serializado
	 * e na cliente não vou permitir os pedidos serem serializados
	 */
	
	
	
	
	
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
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		Produto obj = servico.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//Recuperação de dados é o GET.
	/*
	 * o GET não aceita enviar os parametros no corpo da requisição igual o POST
	 * 
	 * Na requisição lá no POSTMAN quando eu pesquiso eu coloco o 1,2,4.. EX
	 * Mas esse 1,2,4 é um STRING mas a lista que eu inserindo é do tipo Integer
	 * igual eu fiz lá no ProdutoServico, então preciso converter.
	 * 
	 * Para fazer isso vou criar uma classe auxiliar que converta
	 * chamada URL
	 * vou colocar dentro do pacote util pois vão ser utils para trabalhar com meus recursos
	 */
		@RequestMapping(method=RequestMethod.GET)
		public ResponseEntity<Page<ProdutoDTO>> findPage(
				@RequestParam(value="nome", defaultValue="") String nome,
				@RequestParam(value="categorias", defaultValue="") String categorias,
				@RequestParam(value="page", defaultValue="0") Integer page,
				@RequestParam(value="linesPorPage", defaultValue="24") Integer linesPorPage, 
				@RequestParam(value="orderBy", defaultValue="nome" ) String orderBy, 
				@RequestParam(value="direcao", defaultValue="ASC" ) String direcao) {
			/*
			 * Vou criar um metodo auxiliar na minha classe URL
			 * que é para decodificar o nome ou seja pois ele pode ter espaços em branco
			 * E quando utilizamos o ENCODEURICOMPONENT, ele ignora os espaços em branco
			 * colocando o %20..
			 */
			String nomeDecoder = URL.decodeParam(nome);
			
			//Criar uma lista para passa-la como parametro pois lá no CategoriaServico
			//Estou recebendo essa lista de Id como inteiro
			//E como já criei uma classe util chamada URL com essa conversão
			//Basta utiliza-la
			List<Integer> ids = URL.decodeIntList(categorias);
			
			Page<Produto> list = servico.search(nomeDecoder, ids, page, linesPorPage, orderBy, direcao);
			/*
			 * Como o PAGE ele é JAVA 8 compliance, ele não vai precisar nem do STREAM
			 * E nem converter para lista novamente.
			 * 
			 * Criei dentro do produtoDTO o construtor que passa os Produtos 
			 * para dentro do PRODUTODTO.
			 */
			Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
			
			return ResponseEntity.ok().body(listDto);
		}
}
