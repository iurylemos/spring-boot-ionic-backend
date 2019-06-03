package com.iurylemos.cursomc.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	
	/*Associações
	 * Categoria tem vários produtos.
	 * Produtos tem uma categoria
	 * 
	 * Falta mapear os relacionamento
	 * Usando as anotações do JPA para que
	 * na hora de criar o banco eu tenha a referência entre
	 * as duas de muitos para muitos. */
	
	
	//Duas listas de cada lado tenho que colocar o ManyToMany
	//JoinTable nessa anotação define a tabela que vai fazer o
	//muitos para muitos lá no banco relacional.
	//No JoinTable vou dizer que o nome da tabela vai se chamar
	//PRODUTO_CATEGORIA
	//nome do campo da tabela correspondente ao nome do PRODUTO
	//Ou seja a chave estrangeira.
	//joinColumns = nome do campo que vai ser a chave estrangeira
	//nome da outra chave estrangeira que vai ser a categoria
	//ela vai se chamar inserseJoinColumns
	/*
	 * Tá feito o mapeamento da lista de categorias
	 * informando quem vai ser a tabela do banco de dados
	 * que vai fazer o meio de campo, entre as duas tabelas
	 * PRODUTO E CATEGORIA
	 */
	//Esse json back reference fiz tbm na lista de categoria
	//Ou seja do outro lado da ação já foram buscado os objetos
	@JsonBackReference
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA",
		joinColumns = @JoinColumn(name = "produto_id"),
		inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	private List<Categoria> categorias = new ArrayList<>();

	public Produto() {
		
	}

	public Produto(Integer id, String nome, Double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategoria(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	/*
	 * Comparar dois objetos pelo conteudo e não pelo ponteiro.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
