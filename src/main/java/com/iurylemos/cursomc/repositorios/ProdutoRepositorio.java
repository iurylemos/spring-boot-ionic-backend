package com.iurylemos.cursomc.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.dominio.Produto;

@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Integer> {
	//interface capaz de acessar o BD
	//Ela herda o JpaRepository
	/*
	 * Que é um tipo especial do SPRING
	 * capaz de acessar os dados com base em um tipo que você 
	 * passar.
	 * 
	 * e no 2º parametro qual o tipo do atributo identificador do OBJ
	 * no caso como o id é integer.
	 * 
	 */
	
	/*
	 * Metodo que retorna a paginação.
	 * Vou colocar o @Query = Com essa anotação você pode colocar o JPQL aqui dentro ("")
	 * Que ai o framework faz um pré processamento e cria um metodo para gente 
	 * sem ter que criar uma nova classe.
	 * 
	 * Como que eu faço para pegar o valor da variavel que está no parametro String nome?
	 * e jogar dentro da QUERY no campo %:nome% ?
	 * Bastaeu colocar o @Param("nome") que ele já faz
	 * 
	 * E tbm colocar no categorias para identificar que é o mesmo categoria que está na
	 * QUERY.
	 */
	/**
	 * 
	 * Além do nome e além do id, ela vai ter os parametros de paginação.
	 * no MANUAL do SPRING DATA tem uma parte que fala das consultas por nome
	 * onde pode usar várias palavras para ele montar a consulta automaticamente
	 * 
	 * É possivel montar a mesma consulta
	 * Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	 * Usando o padrão de nomes do SPRING DATA
	 * Que é o findDistinctByNomeContainingAndCategoriasIn
	 * utilizando o metodo no caso de id..
	 * E vou colocar o Containing pois ele aplica o LIKE no meu argumento
	 * E vou colocar o AND colocando o CategoriasIN
	 * findDistinctByNome
	 * 
	 */
	
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	//@Param("nome") String nome, @Param("categorias") Retirando até esses daqui do parametro.
	
	//Coloquei o Transaction pois é apenas uma consulta não vai ser necessário realizar uma transação.
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	

}
