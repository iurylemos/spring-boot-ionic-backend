package com.iurylemos.cursomc.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iurylemos.cursomc.dominio.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
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
	
	/***
	 *  Metodo que retorna um email
	 *  Quando eu coloco Cliente findByEmail, o Spring automaticamente já interpreta
	 *  que você quer fazer uma busca por email.
	 *  
	 *  Preciso colocar a anotação Transactional e vou dizer que ela é uma readOnly
	 *  ou seja ela não precisa estar envolvida com uma transação do banco de dados.
	 *  Isso faz ela ficar mais rápida e diminui o que chamamos de looking
	 *  no gerenciamento de transações do banco de dados.
	 *  Vou lá no ClienteInsertValidator e fazer um metodo para olhar se esse
	 *   cliente já existe.
	 */
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
	

}
