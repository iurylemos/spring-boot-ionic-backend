package com.iurylemos.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.dominio.Cidade;
import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.dominio.Endereco;
import com.iurylemos.cursomc.dominio.Estado;
import com.iurylemos.cursomc.dominio.Produto;
import com.iurylemos.cursomc.dominio.enums.TipoCliente;
import com.iurylemos.cursomc.repositorios.CategoriaRepositorio;
import com.iurylemos.cursomc.repositorios.CidadeRepositorio;
import com.iurylemos.cursomc.repositorios.ClienteRepositorio;
import com.iurylemos.cursomc.repositorios.EnderecoRepositorio;
import com.iurylemos.cursomc.repositorios.EstadoRepositorio;
import com.iurylemos.cursomc.repositorios.ProdutoRepositorio;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
		
		/*
		 * Macete para executar a instaciação no momento em
		 * que a aplicação iniciar.
		 * vou botar para que a classe implemente
		 * CommandLineRunner = Permitir implementar um metodo
		 * auxiliar para executar alguma ação
		 * quando a aplicação iniciar.
		 */
	}

	@Override
	public void run(String... args) throws Exception {
		//Instaciação dos meus objetos.
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		//Instanciando os produtos.
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		//Lista de associações
		/*
		 * Na lista de cat1, passo os produtos que estão associados
		 * com o cat1, de acordo com a UML
		 */
		//Aqui vou colocar os produtos que estão associados com as Categorias
		//As categorias agora conhecem os produtos que estão associados com elas
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Os produtos vão conhecer as categorias que estão com eles
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
	
		
		//salvar
		categoriaRepositorio.save(Arrays.asList(cat1, cat2));
		produtoRepositorio.save(Arrays.asList(p1, p2, p3));
		
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
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("32307858", "988572721"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300" , "Apto 303", "Bom Jardim", "60383282", cli1 , c1);
		Endereco e2 = new Endereco(null, "Avenidade Matos", "105" , "Sala 800", "Centro", "60353322", cli1 , c2);
		
		//Associar o cliente a conhecer os endereco.
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		//Salvando no banco com os repositórios.
		//Salvo primeiro quem é independente que é o cliente.
		//E depois salvo os enderecos.
		clienteRepositorio.save(Arrays.asList(cli1));
		enderecoRepositorio.save(Arrays.asList(e1, e2));
	}

}
