package com.iurylemos.cursomc.dominio;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	//Para vim no formato de Data no JSON
	//pattern = padrão.
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
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
	//@JsonManagedReference = O PEDIDO PODE SERIALIZAR O PAGAMENTO
	//@JsonBackReference = O PAGAMENTO NÃO PODE SERIALIZAR O PEDIDO
	//@JsonManagedReference
	//Na ultima atualizado foi retirado o JsonManagedReference.
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;
	
	//pedido tem um cliente
	//Cliente conhece o pedido
	/* eu vou permitir que seja serializado o cliente de um pedido
	 * porem não permitir que seja serializado os pedidos de um cliente
	 * 
	 * isso quer dizer que na PEDIDO vou permitir o cliente ser serializado
	 * e na cliente não vou permitir os pedidos serem serializados
	 */
	//@JsonManagedReference
	//Na ultima atualizado foi retirado o JsonManagedReference.
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	//Pedido tem endereço de entrega
	//Endereço não conhece pedido
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	//Conhecer os item de pedidos associados a ela.
	//O pedido tem vários itens.
	//Foi mapeado pelo id.pedido
	//Porque?
	/*
	 * Do outro lado eu tenho o ItemPedidoPK que tem o objeto id
	 * que eu instanciei e esse id é um objeto auxiliar
	 * que vai ter a referencia para o pedido que tem dentro
	 * da classe ItemPedidoPK
	 */
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	
	public Pedido() {
		
	}

	public Pedido(Integer id, Date instantePedido, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instantePedido = instantePedido;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	/*
	 * Metodo para retornar o valor total desses pedidos.
	 */
	public double getValorTotal() {
		/*
		 * Para calcular o valor total dos pedidos, basta eu pecorrer a lista
		 * de itens pedidos e somar o subTotal de cada pedido atribuindo a variavel
		 * auxiliar que eu criei que é a soma.
		 */
		 double soma = 0.0;
		 /*for(ItemPedido ip: itens) {
			soma = soma + ip.getSubTotal();
		} */
		//Expressão lambda.
		soma = itens.stream().mapToDouble(ItemPedido::getSubTotal).sum();
		return soma;
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

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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

	/*
	 * Botei só o id, por que vou fazer a implementação na mão mesmo.
	 */
	
	@Override
	public String toString() {
		//Formatando os valores na hora de imprimir. chamo o NumberFormat do JAVA mesmo
		//Vou instacia-lo chamando o metodo getCurrencyInstance que é 
		//uma instancia de dinheiro.
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		//Formatação da data.
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(" , Instante: ");
		builder.append(sdf.format(getInstantePedido()));
		builder.append(" , Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(" , Situação do pagamento: ");
		//Acessar o pagamento, acessar o estado do pagamento e acessar a descrição
		//Para vir a mensagem que tem sobre o estado do pagamento
		builder.append(getPagamento().getEstado().getDescricao());
		builder.append("\n Detalhes: \n");
		//Itens do pedido na lista de itens aqui da minha classe pedido.
		for(ItemPedido ip : getItens()) {
			//Concateno dizendo que cada item vai chamar o seu toString e imprimir aqui no meu pedido
			builder.append(ip.toString());
		}
		builder.append("Valor total: ");
		builder.append(nf.format(getValorTotal()));
		return builder.toString();
	}

	
	
}
