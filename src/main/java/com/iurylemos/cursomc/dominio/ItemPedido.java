package com.iurylemos.cursomc.dominio;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;


	/*
	 * Quem identifica ela, são os dois objetos associados a ela
	 * o Produto e o Pedido.
	 * 
	 * E para isso implementar isso no Java com JPA
	 * a forma mais rigorosa é você criar uma chave composta
	 * contendo o produto e o pedido
	 * Vamos criar aqui no Dominio, uma classe auxiliar
	 * chmada ItemPedidoPK
	 * ou seja vai ser a chave primária que é a chave composta
	 * da minha classe ItemPedido.
	 */
	
	//Falando que essa classe vai ter como id o ItemPedidoPK
	/*
	 * Esse id ele é atributo composto
	 * Quando você faz uma entidade do JPA tendo como atributo
	 * uma outra classe
	 * temos que ir na outra classe e colocar o @Enadeddable
	 * Anotação de chave composta.
	 * @EmbeddedId
	 * Ou seja ele é um ID embutido em um tipo auxiliar.
	 */
	//JsonIgnore = NEM OLHA PARA ESSE CARA AQUI 
		//A partir do ItemPedido ele não serializa nem o Pedido nem o Produto
		//Só vou permitir que o pedido serialize os itens dele.
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();
	
	
	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {
		
	}

	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		/*
		 * Um problema esse ItemPedidoPK é uma pericuralidade do JPA
		 * Para os programadores que forem utilizar a minha classe
		 * esse objeto não faz o menor sentido.
		 * Então troquei o ItemPedidoPK id
		 * Por Pedido pedido, Produto produto.
		 * e dentro do construtor vou atribuir o pedido e o produto
		 * que vier, para dentro do objeto ID.
		 */
		super();
		id.setPedido(pedido);
		id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	/*
	 * Calcular o SUB-TOTAL dos precos dos pedidos - desconto * quantidade.
	 * Vou colocar o get pois é reconhecido pelo JSON e vai ser serializado.
	 * vai aparece no item do sub pedido lá no Postman, dizendo o valor total dele.
	 */
	
	public double getSubTotal() {
		return (preco - desconto) * quantidade;
	}
	
	
	
	//Vou criar os gets do Pedido e dos Produtos
	//Isso daqui é para eu ter acesso direto
	//Ao pedido e produto fora da minha classe ItemPedido.
	//Isso faz muito mais sentido do que exigir
	//Que seja acesso primeiro o ID e depois dentro desse id
	//eu ter que acessar o produto ou pedido.
	//É melhor eu ter aqui direta para melhorar semântica da minha classe.
	//Eles dois estão fazendo a referência ciclica
	//Pois tudo que começa com GET ele serializa.
	//Vou ignorar todos dois quando for fazer a serialização
	//com o JsonIgnore
	 
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	// @JsonIgnore
	//Retirei pois quero que quando eu puxe no Postman, venha associado com os ITEN
	public Produto getProduto() {
		return id.getProduto();
	}
	

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
