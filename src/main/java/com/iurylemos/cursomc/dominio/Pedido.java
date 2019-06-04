package com.iurylemos.cursomc.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Pedido implements Serializable {
	
	/**
	 * Serializable = Interface que diz que essa classe aqui
	 * os seus objetos podem ser convertidos para uma sequencia de bytes
	 * Para que os objetos possam ser gravados em arquivos
	 * e para que possam trafegar em redes.
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Date instantePedido;
	
	//Associações
	//Pagamento e pedido é um para um
	//OnetoOne
	//Cascade é necessário pois
	//ele dar erro de EntidadeTransaction
	//quando você vai salvar o pedido e o pagamento dele.
	//E digo que esse atributo aqui foi mapeado pelo pedido
	//Que está lá na classe pagamento
	//Com isso a gente faz o mapeamento bidirecional
	//1 para 1
	//E ainda garantindo que o ID do pagamento
	//Vai ser o mesmo Id do pedido correspondente a ELE.
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;
	
	//pedido tem um cliente
	//Cliente conhece o pedido
	private Cliente cliente;
	
	//Pedido tem endereço de entrega
	//Endereço não conhece pedido
	private Endereco enderecoDeEntrega;
	
	public Pedido() {
		
	}

	public Pedido(Integer id, Date instantePedido, Pagamento pagamento, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instantePedido = instantePedido;
		this.pagamento = pagamento;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstantePedido() {
		return instantePedido;
	}

	public void setInstantePedido(Date instantePedido) {
		this.instantePedido = instantePedido;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	//HashCodeEquals Objetos não podem ser iguais.
	
}
