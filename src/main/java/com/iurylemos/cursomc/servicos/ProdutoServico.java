package com.iurylemos.cursomc.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.dominio.Produto;
import com.iurylemos.cursomc.repositorios.CategoriaRepositorio;
import com.iurylemos.cursomc.repositorios.ProdutoRepositorio;
import com.iurylemos.cursomc.servicos.exceptions.ObjetoNotFountException;

@Service
public class ProdutoServico {
	//Operação capaz de buscar uma categoria por código.
	
	//Depedencia de quem puxa as informações do banco.
	//@Autowired essa dependência vai ser automaticamente instanciada pelo Spring
	//Pelo mecanismo de injeção de depedência ou inversão de controle.
	@Autowired
	private ProdutoRepositorio repo;
	
	@Autowired
	private CategoriaRepositorio categoriaRepositorio;
	
	public Produto find(Integer id) {
		//Implementar um servico que busca um produto.
		//Busco o ID e retorno ele.
		//findOne, se o Id existe, ele retorna, se não é nulo.
		
		Produto obj = repo.findOne(id);
		if(obj == null ) {
			//Lançando uma excessão caso esse ID não exista.
			throw new ObjetoNotFountException("O objeto não encontrado! Id:" +id
				+ ", Tipo: " +Produto.class.getName());
		}
		return obj;
	}
	
	//Passo 2 e 3 eu conseguir indetificar que estava faltando isso no meu sistema.
	//Operação de procura que é o search
	//Recebe esse pedido como argumento e retorna a instãncia monitorada do pedido inserido
	//Como estou recebendo o numéro que é codigoId, vou ter que salvar no banco de dados
	//Vou ter que projetar para salvar os pedidos no banco de dados
	
	/**
	 * 
	 * Além do nome e além do id, ela vai ter os parametros de paginação.
	 * no MANUAL do SPRING DATA tem uma parte que fala das consultas por nome
	 * onde pode usar várias palavras para ele montar a consulta automaticamente
	 * 
	 * É possivel montar a mesma consulta @Query(SELECT...
	 * Usando o padrão de nomes
	 * utilizando o metodo no caso de id..
	 * E vou colocar o Containing pois ele aplica o LIKE no meu argumento
	 * findDistinctByNome
	 * 
	 */
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPorPage, String orderBy, String direcao) {
		//Operação de busca paginada 
		//Para uma consulta para retornar uma pagina de dados
		//Tenho que criar um outro objeto do tipo PageRequest do Spring data
		//Ele vai ser um objeto que vai preparar aqui para minhas informações
		//Para que eu faça a consulta que me retorna a minha pagina de dados.
		//O utlimo que é properties é por onde eu quero ordenar.
		PageRequest pageRequest = new PageRequest(page, linesPorPage, Direction.valueOf(direcao), orderBy);
		//Essa lista acima vai ser uma lista de categorias.
		//Essa lista vai ser instanciada a partir da lista de ID
		//O repositorio busca todas as categorias pelo ID dessa lista do parametro
		List<Categoria> categorias = categoriaRepositorio.findAll(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome,categorias, pageRequest);
	}
	

}
