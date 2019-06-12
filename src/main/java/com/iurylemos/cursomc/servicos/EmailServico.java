package com.iurylemos.cursomc.servicos;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.iurylemos.cursomc.dominio.Cliente;
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
	
	/***
	 * No caso a gente já tem os mesmo metodos para enviar o email normal com textos planos
	 * Agora vou fazer a inserção dos emails utilizando o HTML
	 */
	
	void enviarEmailConfirmacaoHtmlPedido(Pedido obj);
	
	void enviarHtmlEmail(MimeMessage msg);
	
	/*
	 * A implementação do metodo abaixo vai está no AbstractEmailServico
	 */
	
	void enviarNovaSenhaEmail(Cliente cliente, String newSenha);
	

}
