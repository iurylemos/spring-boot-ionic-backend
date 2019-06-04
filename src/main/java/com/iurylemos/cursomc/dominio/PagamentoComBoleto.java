package com.iurylemos.cursomc.dominio;

import java.util.Date;

import com.iurylemos.cursomc.dominio.enums.EstadoPagamento;

public class PagamentoComBoleto extends Pagamento {

	private static final long serialVersionUID = 1L;
	private Date dataVencimento;
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
		
	}
	
	/*
	 * Como o Pagamento com Boleto ele é uma SUBCLASSE
	 * utilizamos o Generate Construtors from SuperClass
	 */
	

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}
