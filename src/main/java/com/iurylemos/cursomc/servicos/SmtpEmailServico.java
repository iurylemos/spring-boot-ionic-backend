package com.iurylemos.cursomc.servicos;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailServico extends AbstractEmailServico {
	
	//Injetar uma instancia do MailSender
	/*
	 * É uma classe do framework que quando você instancia um objeto dessa classe
	 * automaticamente ele vai pegar os dados que você estabeleceu no application-dev.properties
	 * e instanciar o objeto com esses dados
	 * ELE VAI SER UM OBJETO PRONTO PARA ENVIAR EMAIL COM OS DADOS QUE VOCÊ INFORMOU.
	 */
	@Autowired
	private MailSender mailSender;
	
	//Para enviar em HTML tem que ser o JavamailSender
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailServico.class);
	

	@Override
	public void enviarEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg);
		LOG.info("E-mail enviado com êxito");
		
	}


	@Override
	public void enviarHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email Html..");
		javaMailSender.send(msg);
		LOG.info("E-mail enviado com êxito!");
	}

}
