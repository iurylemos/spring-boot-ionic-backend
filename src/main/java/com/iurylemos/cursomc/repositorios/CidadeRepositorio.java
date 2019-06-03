package com.iurylemos.cursomc.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iurylemos.cursomc.dominio.Cidade;

@Repository
public interface CidadeRepositorio extends JpaRepository<Cidade, Integer> {
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
	
	

}
