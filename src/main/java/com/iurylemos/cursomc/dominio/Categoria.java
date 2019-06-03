package com.iurylemos.cursomc.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

//Persistence sempre pois é do JPA
@Entity
public class Categoria implements Serializable {
	
	/**
	 * Serializable = Interface que diz que essa classe aqui
	 * os seus objetos podem ser convertidos para uma sequencia de bytes
	 * Para que os objetos possam ser gravados em arquivos
	 * e para que possam trafegar em redes.
	 */
	
	private static final long serialVersionUID = 1L;
	
	//Geração automatica do ID com o GeneratedValue
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	/*Associações
	 * Categoria tem vários produtos.
	 * Produtos tem uma categoria */
	
	/*
	 * Fazendo o mapeamento
	 * Aqui não vou precisar fazer o mapeamento com a query
	 * igual eu fiz na classe produto não
	 * basta que eu faça o mapeamento de acordo com o nome da lista
	 * que tem lá o mapeamento.
	 * No caso está realizado o mapeamento muito para muitos
	 * dos dois lados.
	 */
	
	//Lista
	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos = new ArrayList<>();
	
	
	
	public Categoria() {
		
	}
	
	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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

	//Get e set da lista.
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	//Comparar objetos pelo conteudo, ou seja pelo valor.
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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
