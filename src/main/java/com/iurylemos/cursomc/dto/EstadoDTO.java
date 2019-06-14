package com.iurylemos.cursomc.dto;

import java.io.Serializable;

import com.iurylemos.cursomc.dominio.Estado;

public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//O que esse objeto vai ser?
	//Vai ser simplesmente para definir os dados que eu quero trafegar quando for fazer
	//Operações básicas de categoria.
	//Excemplo quando eu utilizar o findAll eu não quero visualizar os produtos dessa categoria
	//Quero simplesmente a categoria. e é isso que essa classe faz.
	
	private Integer id;
	private String nome;
	
	public EstadoDTO() {
		
	}
	
	public EstadoDTO(Estado obj) {
		//Atribuindo o ID do meu DTO ao objeto que está vindo da categoria
		//E assim só vai exibir o id e o nome daquela categoria e não os produtos.
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
