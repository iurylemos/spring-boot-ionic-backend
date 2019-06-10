package com.iurylemos.cursomc.servicos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailServico extends AbstractEmailServico {
	
	//Injetar uma instancia do MailSender
	/*
	 * É uma classe do framework que quando você instancia um objeto dessa classe
	 * automaticamente ele vai pegar os dados que você estabeleceu no application-dev.properties
	 * e instanciar o objeto com esses dados
	 * ELE VAI SER UM OBJETO PRONTO PARA ENVIAR EMAIL COM OS DADOS QUE VOCÊ INFORMOU.
	 */
	@Autowired
	MailSender mailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailServico.class);
	

	@Override
	public void enviarEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		mailSender.send(msg);
		LOG.info("E-mail enviado com êxito");
		
	}

}
