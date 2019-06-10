package com.iurylemos.cursomc.servicos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class FalsoEmailServico extends AbstractEmailServico {

	//Para mostrar lá no log do servidor, temos que instanciar o Logger.
	//Criei ele como estático para toda vez que for chamado essa classe aqui
	//Não precise criar outro, vai ser um só para todos.
	//Vai ser um logger referente a essa classe aqui.
	private static final Logger LOG = LoggerFactory.getLogger(FalsoEmailServico.class);
	
	@Override
	public void enviarEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de e-mail");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
		
	}

}
