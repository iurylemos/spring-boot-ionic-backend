package com.iurylemos.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.iurylemos.cursomc.dominio.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//O que esse objeto vai ser?
	//Vai ser simplesmente para definir os dados que eu quero trafegar quando for fazer
	//Operações básicas de categoria.
	//Excemplo quando eu utilizar o findAll eu não quero visualizar os produtos dessa categoria
	//Quero simplesmente a categoria. e é isso que essa classe faz.
	
	private Integer id;
	
	//Validação
	//Caso o campo não tenha sido preenchido, vai dar a mensagem de preenchimento obrigatorio
	//No length que é o tamanho o minimo é 5 e o maximo é 80 caracteres.
	
	@NotEmpty(message="Preenchimento obrigatorio")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 8 caracteres")
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(Categoria obj) {
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
