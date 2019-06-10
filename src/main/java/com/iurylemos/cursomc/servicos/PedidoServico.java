package com.iurylemos.cursomc.servicos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iurylemos.cursomc.dominio.ItemPedido;
import com.iurylemos.cursomc.dominio.PagamentoComBoleto;
import com.iurylemos.cursomc.dominio.Pedido;
import com.iurylemos.cursomc.dominio.enums.EstadoPagamento;
import com.iurylemos.cursomc.repositorios.ItemPedidoRepositorio;
import com.iurylemos.cursomc.repositorios.PagamentoRepositorio;
import com.iurylemos.cursomc.repositorios.PedidoRepositorio;
import com.iurylemos.cursomc.servicos.exceptions.ObjetoNotFountException;

@Service
public class PedidoServico {
	//Operação capaz de buscar uma categoria por código.
	
	//Depedencia de quem puxa as informações do banco.
	//@Autowired essa dependência vai ser automaticamente instanciada pelo Spring
	//Pelo mecanismo de injeção de depedência ou inversão de controle.
	@Autowired
	private PedidoRepositorio repo;
	
	@Autowired
	private BoletoServico boletoServico;
	
	@Autowired
	private PagamentoRepositorio pagamentoRepositorio;
	
	//No lugar de colocar o ProdutoRepositorio colocar o ProdutoServico
	
	@Autowired
	private ProdutoServico produtoServico;
	
	@Autowired
	private ItemPedidoRepositorio itemPedidoRepositorio;
	
	@Autowired
	private ClienteServico clienteServico;
	
	@Autowired
	private EmailServico emailServico;
	
	public Pedido find(Integer id) {
		//Implementar um servico que busca uma categoria.
		//Busco o ID e retorno ele.
		//findOne, se o Id existe, ele retorna, se não é nulo.
		
		Pedido obj = repo.findOne(id);
		if(obj == null ) {
			//Lançando uma excessão caso esse ID não exista.
			throw new ObjetoNotFountException("O objeto não encontrado! Id:" +id
				+ ", Tipo: " +Pedido.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		//Inserindo nulo no ID para garantir que estou inserindo nulo.
		obj.setId(null);
		obj.setInstantePedido(new Date());
		//Setando o cliente no insert
		//Ou seja nesse objeto que tinha só o ID getCliente
		//Eu vou usar esse ID para buscar do banco de dados que está no clienteServico
		//e lá está CLIENTE INTEIRO e seto ele como objeto associado ao meu obj
		obj.setCliente(clienteServico.find(obj.getCliente().getId()));
		
		
		//Pagamento
		//Um pedido que estou acabando de inserir o estado dele ainda está PENDENTE.
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		//Associação de mão dupla o pagamento tem que conhecer o pedido dele.
		obj.getPagamento().setPedido(obj);
		//Data de Vencimento vai ser uma semana depois da data do pedido.
		/*
		 * Se o meu PAGAMENTO for do tipo PagamentoComBoleto
		 * vou gerar uma data para ele
		 */
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoServico.preencherPagamentoComBoleto(pagto, obj.getInstantePedido());
			/*
			 * Esse metodo preencher.. vai ter que preencher nesse pagto a data de VENCIMENTO.
			 */
		}
		//Salvei o pedido
		obj = repo.save(obj);
		//Salvei o pagamento
		pagamentoRepositorio.save(obj.getPagamento());
		//Salvando os itens do pedido.
		//Como os descontos são zero
		//Vou pecorrer a lista de ItemPedidos associados ao meu OBJ
		//E para cada item desse eu atribuo o zero.
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			/*
			 * Esse produtoServico.find(ip.getProduto().getId())
			 * Tenho que setar ele como produto do meu ITEM. ItemProduto
			 * Agora o meu item de pedido está associado com o produto
			 * que eu busquei do banco de dados
			 * Agora vou usar esse produto para associar ao preço do meu item.
			 */
			ip.setProduto(produtoServico.find(ip.getProduto().getId()));
			//getId por que? Por que o ID vai está presente no produto que eu mandei
			//Pego esse id e busco no BD o produto inteiro
			//De posse com o produto inteiro na mão eu chamo o getPreco
			//Agora eu estou setando o preco do ItemPedido como mesmo preco do Produto.
			ip.setPreco(ip.getProduto().getPreco());
			//Associando o item de pedido com o pedido que estou inserindo que é o obj.
			ip.setPedido(obj);
		}
		itemPedidoRepositorio.save(obj.getItens());
		//Tenho que definir que essa interface vai ser instaciada como um FalsoEmailServico
		emailServico.enviarEmailConfirmacaoPedido(obj);
		return obj;
	}
	

}
