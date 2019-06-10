package com.iurylemos.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.iurylemos.cursomc.servicos.DBServico;
import com.iurylemos.cursomc.servicos.EmailServico;
import com.iurylemos.cursomc.servicos.FalsoEmailServico;

@Configuration
@Profile("test")
public class TestConfig {
	/*
	 * Primeira anotação de que é uma configuração.
	 * E a segunda é para indicar que essa configuração é especifica do
	 * profile de test, igual configurei no arquivo application-teste.properties
	 * "test"
	 * E com isso eu digo que todos os beans dessa classe vão ser ativados
	 * apenas quando o profile de test estiver ativo no meu apllication.properties
	 * 
	 * 
	 * 
	 * Configurações especificas do profile de teste que é aquele
	 * arquivo que eu copiei do application.properties, criando um novo profile.
	 * 
	 * 
	 * 
	 * Criei essa configuração de teste dentro dela chamo a instanciação
	 * que agora está em uma configuração de servico chamada DBServico
	 * ela que vai ser responsável por conhecer como que eu tenho que instanciar
	 * um banco de dados de TESTE.
	 */
	
	
	//Depedência da classe que está utilizando o BD
	@Autowired
	private DBServico dbServico;
	
	
	//Criar um bean
	/*
	 * Coloquei boolean só por que não pode retornar vázio, ai vou 
	 * retornar true.
	 */
	

	
	
	
	@Bean
	public boolean instaciacaoDatabase() throws ParseException {
		/*
		 * Aqui dentro vou tranportar todos os arquivos do banco de dados
		 * que está dentro da aplicacaoPrincipal para um serviço específico para isso
		 * 
		 * Vou utilizar o metodo que está utilizando o banco de dados
		 */
		
		dbServico.instanciacaoTestDatabase();
		
		return true;
	}
	
	/*
	 * Metodo para envio de enviarEmailFalso
	 * 
	 * O IMPORTANTE A SE ENTENDER AQUI É QUE
	 * QUANDO A GENTE FAZ UM METODO A ANOTAÇÃO @BEAN
	 * ESSE CARA VAI ESTAR DISPONÍVEL COMO UM COMPONENTE AQUI NO SISTEMA
	 * ENTÃO SE EM OUTRA CLASSE QUE REALIZAMOS UMA INJEÇÃO DE DEPEDÊNCIA 
	 * EMAILSERVICO IGUAL NO PEDIDOSERVICO
	 * O SPRING VAI PROCURAR POR UM COMPONENTE QUE PODE SER @BEAN
	 * QUE ME DEVOLVE UMA INSTÃNCIA DESSE CARA.
	 * AI ELE VAI ENCONTRAR ESSE METODO ABAIXO
	 * E VAI ME RETORNAR UMA NOVA INSTANCIA DO FALSOEMAILSERVICO()
	 * ISSO É AUTOMATICO.
	 */
	
	@Bean
	public EmailServico emailServico() {
		return new FalsoEmailServico();
	}

}
