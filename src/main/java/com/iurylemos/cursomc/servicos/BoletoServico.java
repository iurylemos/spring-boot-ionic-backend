package com.iurylemos.cursomc.servicos;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.PagamentoComBoleto;

@Service
public class BoletoServico {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		/*
		 * Quero vim nesse pagto e colocar nele a dataDeVencimento
		 * sendo 7 dias depois dessa data.
		 * 
		 * Isso é só uma brincadeirinha.. 
		 * Se fosse em situação REAL
		 * Você teria que trocar essa implementação aqui
		 * pela chamada de um WEBSERVICE que gera o boleto para mim.
		 * Ai eu pegaria a data do Vencimento do boleto.
		 *  
		 * 
		 */
		//Instancia de calendario
		Calendar cal = Calendar.getInstance();
		//Definir a data
		cal.setTime(instanteDoPedido);
		//Acrescentar nessa data 7 dias.
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}

}
