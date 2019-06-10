package com.iurylemos.cursomc.servicos;

import org.springframework.mail.SimpleMailMessage;

import com.iurylemos.cursomc.dominio.Pedido;

public interface EmailServico {
	/*
	 * Interface de Servico de Email
	 * Ela vai ser um contrato
	 * Quais operações que o Servico de Email deve oferecer.
	 */
	
	void enviarEmailConfirmacaoPedido(Pedido obj);
	
	/*
	 * Essa operação vai receber um objeto do SimpleMailMessage
	 */
	void enviarEmail(SimpleMailMessage msg);

}
