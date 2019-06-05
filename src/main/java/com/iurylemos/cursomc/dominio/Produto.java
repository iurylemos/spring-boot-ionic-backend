package com.iurylemos.cursomc.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	//Conhecer os item de pedidos associados a ela.
		//O pedido tem vários itens.
		//Foi mapeado pelo id.pedido
		//Porque?
		/*
		 * Do outro lado eu tenho o ItemPedidoPK que tem o objeto id
		 * que eu instanciei e esse id é um objeto auxiliar
		 * que vai ter a referencia para o pedido que tem dentro
		 * da classe ItemPedidoPK
		 * 
		 * O importante é que a partir do item de Pedido eu tenha
		 * acesso ao Produto e não do Produto ter acesso ao Item 
		 * de Pedido..
		 * Por isso vou colocar o @jsonIgnore
		 */
	@JsonIgnore
	@OneToMany(mappedBy="id.produto")
	private Set<ItemPedido> itens = new HashSet<>();

	public Produto() {
		
	}

	public Produto(Integer id, String nome, Double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	/*
	 * Um produto ele conhece os pedidos dele
	 * Então vou criar um metodo getPedidos
	 * varrendo os itens de pedido e montando
	 * uma lista de pedidos associados a esses itens.
	 * 
	 * 
	 * o nome do metodo tem que ser getPedidos
	 * para obedecer o padrão do JavaBeans.
	 * que é uma exigência da plataforma java.
	 * tem que começar o nome com get e depois colocar o nome
	 * que você quer do DADO.
	 */
	//Ignorar tbm o getPedidos, pois a partir dos itens 
	//Que eu quero os produtos e não vice versa.
	//Se eu deixar sem ignorar, vai ser serializados os pedidos 
	//associados aos produtos, e eu não quero isso.
	
	@JsonIgnore
	public List<Pedido> getPedidos() {
		//Iniciando uma lista de Pedido
		List<Pedido> lista = new ArrayList<>();
		//Agora vou pecorrer todos os ItemPedido
		//E para cada itemdePedido x que existir na lista itens
		//vou adicionar o pedido associado a ele na minha lista.
		for(ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
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

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
