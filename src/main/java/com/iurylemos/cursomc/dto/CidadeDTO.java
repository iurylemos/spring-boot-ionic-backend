package com.iurylemos.cursomc.dto;

import java.io.Serializable;

import com.iurylemos.cursomc.dominio.Cidade;

public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//O que esse objeto vai ser?
	//Vai ser simplesmente para definir os dados que eu quero trafegar quando for fazer
	//Operações básicas de Cidade.
	//Excemplo quando eu utilizar o findAll eu não quero visualizar os produtos dessa Cidade
	//Quero simplesmente a Cidade. e é isso que essa classe faz.
	
	private Integer id;
	private String nome;
	
	public CidadeDTO() {
		
	}
	
	public CidadeDTO(Cidade obj) {
		//Atribuindo o ID do meu DTO ao objeto que está vindo da Cidade
		//E assim só vai exibir o id e o nome daquela Cidade e não os produtos.
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
