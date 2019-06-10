package com.iurylemos.cursomc;

import java.text.SimpleDateFormat;
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
import com.iurylemos.cursomc.dominio.ItemPedido;
import com.iurylemos.cursomc.dominio.Pagamento;
import com.iurylemos.cursomc.dominio.PagamentoComBoleto;
import com.iurylemos.cursomc.dominio.PagamentoComCartao;
import com.iurylemos.cursomc.dominio.Pedido;
import com.iurylemos.cursomc.dominio.Produto;
import com.iurylemos.cursomc.dominio.enums.EstadoPagamento;
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

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	

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
	
		
	}

}
