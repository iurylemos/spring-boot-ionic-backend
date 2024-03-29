package com.iurylemos.cursomc.servicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.dominio.Cidade;
import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.dominio.Endereco;
import com.iurylemos.cursomc.dominio.Estado;
import com.iurylemos.cursomc.dominio.ItemPedido;
import com.iurylemos.cursomc.dominio.Pagamento;
import com.iurylemos.cursomc.dominio.PagamentoComBoleto;
import com.iurylemos.cursomc.dominio.PagamentoComCartao;
import com.iurylemos.cursomc.dominio.Pedido;
import com.iurylemos.cursomc.dominio.Produto;
import com.iurylemos.cursomc.dominio.enums.EstadoPagamento;
import com.iurylemos.cursomc.dominio.enums.Perfil;
import com.iurylemos.cursomc.dominio.enums.TipoCliente;
import com.iurylemos.cursomc.repositorios.CategoriaRepositorio;
import com.iurylemos.cursomc.repositorios.CidadeRepositorio;
import com.iurylemos.cursomc.repositorios.ClienteRepositorio;
import com.iurylemos.cursomc.repositorios.EnderecoRepositorio;
import com.iurylemos.cursomc.repositorios.EstadoRepositorio;
import com.iurylemos.cursomc.repositorios.ItemPedidoRepositorio;
import com.iurylemos.cursomc.repositorios.PagamentoRepositorio;
import com.iurylemos.cursomc.repositorios.PedidoRepositorio;
import com.iurylemos.cursomc.repositorios.ProdutoRepositorio;

@Service
public class DBServico {
	/*
	 * Classe de instaciação do banco de dados
	 * Peguei tudo que estava na principal e vou jogar aqui
	 * 
	 * Vou lá na classe TestConfig e vou chamar essa classe aqui para instaciar 
	 * o banco de dados.
	 */

	//Depedência
	@Autowired
	private CategoriaRepositorio categoriaRepositorio;
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Autowired
	private EstadoRepositorio estadoRepositorio;
	
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;
	
	@Autowired
	private PedidoRepositorio pedidoRepositorio;
	
	@Autowired
	private PagamentoRepositorio pagamentoRepositorio;
	
	@Autowired
	private ItemPedidoRepositorio itemPedidoRepositorio;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instanciacaoTestDatabase() throws ParseException {
		//Instaciação dos meus objetos.
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		//Instanciando os produtos.
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritorio", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		Produto p12 = new Produto(null, "Produto 12", 10.00);
		Produto p13 = new Produto(null, "Produto 13", 10.00);
		Produto p14 = new Produto(null, "Produto 14", 10.00);
		Produto p15 = new Produto(null, "Produto 15", 10.00);
		Produto p16 = new Produto(null, "Produto 16", 10.00);
		Produto p17 = new Produto(null, "Produto 17", 10.00);
		Produto p18 = new Produto(null, "Produto 18", 10.00);
		Produto p19 = new Produto(null, "Produto 19", 10.00);
		Produto p20 = new Produto(null, "Produto 20", 10.00);
		Produto p21 = new Produto(null, "Produto 21", 10.00);
		Produto p22 = new Produto(null, "Produto 22", 10.00);
		Produto p23 = new Produto(null, "Produto 23", 10.00);
		Produto p24 = new Produto(null, "Produto 24", 10.00);
		Produto p25 = new Produto(null, "Produto 25", 10.00);
		Produto p26 = new Produto(null, "Produto 26", 10.00);
		Produto p27 = new Produto(null, "Produto 27", 10.00);
		Produto p28 = new Produto(null, "Produto 28", 10.00);
		Produto p29 = new Produto(null, "Produto 29", 10.00);
		Produto p30 = new Produto(null, "Produto 30", 10.00);
		Produto p31 = new Produto(null, "Produto 31", 10.00);
		Produto p32 = new Produto(null, "Produto 32", 10.00);
		Produto p33 = new Produto(null, "Produto 33", 10.00);
		Produto p34 = new Produto(null, "Produto 34", 10.00);
		Produto p35 = new Produto(null, "Produto 35", 10.00);
		Produto p36 = new Produto(null, "Produto 36", 10.00);
		Produto p37 = new Produto(null, "Produto 37", 10.00);
		Produto p38 = new Produto(null, "Produto 38", 10.00);
		Produto p39 = new Produto(null, "Produto 39", 10.00);
		Produto p40 = new Produto(null, "Produto 40", 10.00);
		Produto p41 = new Produto(null, "Produto 41", 10.00);
		Produto p42 = new Produto(null, "Produto 42", 10.00);
		Produto p43 = new Produto(null, "Produto 43", 10.00);
		Produto p44 = new Produto(null, "Produto 44", 10.00);
		Produto p45 = new Produto(null, "Produto 45", 10.00);
		Produto p46 = new Produto(null, "Produto 46", 10.00);
		Produto p47 = new Produto(null, "Produto 47", 10.00);
		Produto p48 = new Produto(null, "Produto 48", 10.00);
		Produto p49 = new Produto(null, "Produto 49", 10.00);
		Produto p50 = new Produto(null, "Produto 50", 10.00);
		
		//Lista de associações
		/*
		 * Na lista de cat1, passo os produtos que estão associados
		 * com o cat1, de acordo com a UML
		 */
		//Aqui vou colocar os produtos que estão associados com as Categorias
		//As categorias agora conhecem os produtos que estão associados com elas
		cat1.getProdutos().addAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20,
				p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38,
				p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		cat3.getProdutos().addAll(Arrays.asList(p5,p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		//Os produtos vão conhecer as categorias que estão com eles
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		p12.getCategorias().add(cat1);
		p13.getCategorias().add(cat1);
		p14.getCategorias().add(cat1);
		p15.getCategorias().add(cat1);
		p16.getCategorias().add(cat1);
		p17.getCategorias().add(cat1);
		p18.getCategorias().add(cat1);
		p19.getCategorias().add(cat1);
		p20.getCategorias().add(cat1);
		p21.getCategorias().add(cat1);
		p22.getCategorias().add(cat1);
		p23.getCategorias().add(cat1);
		p24.getCategorias().add(cat1);
		p25.getCategorias().add(cat1);
		p26.getCategorias().add(cat1);
		p27.getCategorias().add(cat1);
		p28.getCategorias().add(cat1);
		p29.getCategorias().add(cat1);
		p30.getCategorias().add(cat1);
		p31.getCategorias().add(cat1);
		p32.getCategorias().add(cat1);
		p33.getCategorias().add(cat1);
		p34.getCategorias().add(cat1);
		p35.getCategorias().add(cat1);
		p36.getCategorias().add(cat1);
		p37.getCategorias().add(cat1);
		p38.getCategorias().add(cat1);
		p39.getCategorias().add(cat1);
		p40.getCategorias().add(cat1);
		p41.getCategorias().add(cat1);
		p42.getCategorias().add(cat1);
		p43.getCategorias().add(cat1);
		p44.getCategorias().add(cat1);
		p45.getCategorias().add(cat1);
		p46.getCategorias().add(cat1);
		p47.getCategorias().add(cat1);
		p48.getCategorias().add(cat1);
		p49.getCategorias().add(cat1);
		p50.getCategorias().add(cat1);
		
	
		
		//salvar
		categoriaRepositorio.save(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepositorio.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		produtoRepositorio.save(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20,
				p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38,
				p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));
		
		/**
		 * Separando =================
		 */
		
		//Estado
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		//Cidades
		Cidade c1 = new Cidade(null,"Uberlândia", est1);
		Cidade c2 = new Cidade(null,"São Paulo", est2);
		//No MUITOS para UM, no próprio contrutor a gente já faz a instaciação.
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		//Estado conhecendo a cidade.
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		//Temos que salvar os estados já relacionando depois com os estados
		estadoRepositorio.save(Arrays.asList(est1, est2));
		cidadeRepositorio.save(Arrays.asList(c1, c2, c3));
		
		/**
		 * Instaciação dos outros objetos criados, Cliente,Endereco
		 * 
		 * Ordem das depedências.
		 * Endereco Depende do Cliente e da Cidade então é o ULTIMO
		 * Cliente tem endereco e tem telefone então é o PRIMEIRO
		 */
		
		//Instaciação.
		Cliente cli1 = new Cliente(null, "Iury Lemos", "iurylemos10@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("32307858", "988572721"));
		
		Cliente cli2 = new Cliente(null, "Bignardo Gomes", "bignardo@gmail.com", "38534377022", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("34511742", "988542154"));
		cli2.addPerfil(Perfil.ADMIN);
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300" , "Apto 303", "Bom Jardim", "60383282", cli1 , c1);
		Endereco e2 = new Endereco(null, "Avenidade Matos", "105" , "Sala 800", "Centro", "60353322", cli1 , c2);
		Endereco e3 = new Endereco(null, "Avenidade Grande", "2105" , null, "Conj Ceara", "604453322", cli2 , c2);
		
		
		//Associar o cliente a conhecer os endereco.
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		//Salvando no banco com os repositórios.
		//Salvo primeiro quem é independente que é o cliente.
		//E depois salvo os enderecos.
		clienteRepositorio.save(Arrays.asList(cli1, cli2));
		enderecoRepositorio.save(Arrays.asList(e1, e2, e3));
		
		/**
		 * Instaciação dos pedidos e pagamentos
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		//Pagamentos
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		//Pagamento do pedido 1, é o pagamento1 que acabei de criar
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		//Associando o cliente com os pedidos dele.
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		//Tenho que colocar primeiro o pedido pois ele é independente do pagamento.
		pedidoRepositorio.save(Arrays.asList(ped1, ped2));
		pagamentoRepositorio.save(Arrays.asList(pagto1, pagto2));
		
		/***
		 * Criando os itens de pedido e instanciando
		 */
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		//Associando cada pedido com os itens dele.
		//GetItens para acessar o conjunto de itens.
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		//Cada produto conhecendo os seus itens.
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepositorio.save(Arrays.asList(ip1, ip2, ip3));
	}

}
