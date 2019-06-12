package com.iurylemos.cursomc.servicos;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.dominio.Pedido;

public abstract class AbstractEmailServico implements EmailServico {
	
	//Pegando o valor da chave do emailpadrão.
	//E esse vai ser o remetente do meu email
	@Value("${default.sender}")
	private String sender;
	
	
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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

	/*
	 * Criar um metodo auxiliar
	 * para ele pegar o template de email HTML que eu criei
	 * e povoar esse template com os dados de um pedido e retornar para mim
	 * um String correspondendo a esse HTML preenchido
	 * 
	 * COM ESSE METODO AQUI:
	 * PEGA O TEMPLATE, INJETA O OBJETO PEDIDO LÁ DENTRO
	 * PROCESSA O TEMPLATE E ME RETORNAR O HTML NA FORMA DE STRING.
	 * 
	 * 
	 */
	protected String htmlParaTemplatePedido(Pedido obj) {
		//Instanciar um objeto do tipo Context
		//Eu preciso de um objeto como esse para acessar o meu template.
		Context context = new Context();
		//Povar o template com os dados do pedido.
		//Como lá tem pedido.id, pedido.remetente e etc tenho que colocar
		//Define que o meu template vai utilizar o obj que é o pedido
		//Com o nome de pedido.
		context.setVariable("pedido", obj);
		//processar o template para me retornar o html na forma de String.
		//Preciso instanciar do TemplateEngine e essa instanciar vou injetar na minha classe aqui
		/*
		 * No primeiro parametro eu coloco o caminho onde está o template.
		 * e no outro o CONTEXT
		 */
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void enviarEmailConfirmacaoHtmlPedido(Pedido obj) {
		try {
			MimeMessage mm = prepararMimeMessagemDeEmailParaPedido(obj);
			//TemplateMethod a gente tá chamando um metodo que nem está implementado
			//Mas o compilador aceita numa boa..
			enviarHtmlEmail(mm);
		} catch(MessagingException e) {
			//Se acontecer uma messageExceptio na hora de gerar a messagemHtml
			//Vou chamar o metodo para enviar o email de texto plano mesmo..
			enviarEmailConfirmacaoPedido(obj);
		}
			
	}

	//Botei como protected para ser reaproveitado em alguma subclasse.
	//Esse metodo pode gerar uma excessão e no metodo acima vou tratar essa excessão.
	//Antes de acontecer..
	protected MimeMessage prepararMimeMessagemDeEmailParaPedido(Pedido obj) throws MessagingException {
		/*
		 * Pegar o pedido e gerar um objeto do tipo MimeMessage
		 * Para pegar um objeto desse tipo preciso injetar nessa classe
		 * JavaMailSender
		 */
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		//Agora para ter o poder de atribuir valores a essa mensagem
		//Preciso istanciar um MimeMessageHelper
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		//Destinatário
		mmh.setTo(obj.getCliente().getEmail());
		//remetente no caso eu vou pegar o sender.
		mmh.setFrom(sender);
		//Assunto do email
		mmh.setSubject("Pedido Confirmado! Código: "+ obj.getId());
		//Instante do email
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		/*
		 * Corpo do email vai ser o meu emailHtml
		 * Processado a partir do meu template TymeLift
		 */
		mmh.setText(htmlParaTemplatePedido(obj), true);
		return mimeMessage; 
	}
	
	@Override
	public void enviarNovaSenhaEmail(Cliente cliente, String newSenha) {
		//Copiei isso do outro metodo enviarEmailConfirmacaoPedido
		//E o metodo prepararNovaSenhaEmail
		//foi criado abaixo
		SimpleMailMessage sm = prepararNovaSenhaEmail(cliente, newSenha);
		enviarEmail(sm);
		
	}

	//Botei como protected pois as classes filhas vão poder sobrepô-las
	
	protected SimpleMailMessage prepararNovaSenhaEmail(Cliente cliente, String newSenha) {
		//Copiei lá do metodo prepararMensagemEmailPedido
		/*
		 * Gerar um SimpleMailMessage a partir de um pedido
		 * vou instanciar mais um simpleMailMessage
		 * e depois chamar o set para definir alguns atributos básicos
		 * de uma mensagem de email..
		 */
		SimpleMailMessage sm = new SimpleMailMessage();
		//Confirmação do pedido, para quem vai? Para o cliente.
		//Abaixo vai ser o destinatário do email.
		sm.setTo(cliente.getEmail());
		//Remetentente do email, vai ser o padrão ou seja o que eu 
		//definir no application.properties, que está na chave default.sender
		sm.setFrom(sender);
		//Assunto.
		sm.setSubject("Solicitação de nova senha");
		//Data do email, botei o currentMills para garantir 
		//Que vai ser de acordo com o meu servidor.
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//Corpo do Email
		sm.setText("Nova senha: " + newSenha);
		return sm;
	}
}
