package com.iurylemos.cursomc.servicos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.iurylemos.cursomc.dominio.Pedido;

public abstract class AbstractEmailServico implements EmailServico {
	
	//Pegando o valor da chave do emailpadrão.
	//E esse vai ser o remetente do meu email
	@Value("${default.sender}")
	private String sender;
	
	
	/*
	 * Dando corpo a um metodo do EmailServico
	 */
	@Override
	public void enviarEmailConfirmacaoPedido(Pedido obj) {
		//Vou chamar a classe que faz a referência ao EMAIL
		/*
		 * Vou instanciar o meu SimpleMailMessage a apartir do meu metodo
		 * que é o Pedido.
		 * 
		 * Esse enviarEmail é aquele metodo da minha interface.
		 * 
		 * Estou chamando ele aqui no meu AbstractEmailServico
		 * Ele não está implementado ainda, mas já posso utiliza-lo aqui
		 * na minha implementação da classe abstrata, esse é o padrão de projeto
		 * Template Method, que você consegue implementar um metodo baseado em
		 * metodos abstratos, que depois vão ser implementados pelas implementações 
		 * da interface EmailServico.
		 */
		SimpleMailMessage sm = prepararMessagemDeEmailParaPedido(obj);
		enviarEmail(sm);
	}

	/*
	 * Troquei a restrição para protected
	 * Por que? 
	 * Por que esse metodo pode ser acessado pelas sub-classe.
	 * Só não poder ser acessado pelos usuáriso da minha classe
	 * que são os controladores e serviços.
	 */
	protected SimpleMailMessage prepararMessagemDeEmailParaPedido(Pedido obj) {
		/*
		 * Gerar um SimpleMailMessage a partir de um pedido
		 * vou instanciar mais um simpleMailMessage
		 * e depois chamar o set para definir alguns atributos básicos
		 * de uma mensagem de email..
		 */
		SimpleMailMessage sm = new SimpleMailMessage();
		//Confirmação do pedido, para quem vai? Para o cliente.
		//Abaixo vai ser o destinatário do email.
		sm.setTo(obj.getCliente().getEmail());
		//Remetentente do email, vai ser o padrão ou seja o que eu 
		//definir no application.properties, que está na chave default.sender
		sm.setFrom(sender);
		//Assunto.
		sm.setSubject("Pedido confirmado! Código: " +obj.getId());
		//Data do email, botei o currentMills para garantir 
		//Que vai ser de acordo com o meu servidor.
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//Corpo do Email
		sm.setText(obj.toString());
		return sm;
	}

}
