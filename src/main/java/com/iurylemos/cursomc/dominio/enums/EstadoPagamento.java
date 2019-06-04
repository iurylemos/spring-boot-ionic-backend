package com.iurylemos.cursomc.dominio.enums;

public enum EstadoPagamento {
	
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int codigo;
	private String descricao;
	
	private EstadoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	//Recebe um código e retorna um objeto do TipoCliente instanciado
	//Stático pois não vou precisar criar um objeto
	public static EstadoPagamento toEnum(Integer cod) {
		//Programação defensiva
		if(cod == null ) {
			return null;
		}
		//todos objetos x no tipo cliente
		for(EstadoPagamento x : EstadoPagamento.values()) {
			//Se o codigo que estou verificando for igual ao codigo estou procurando
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		//Se esgotar e não for ninguém, ai eu lanço uma excessao.
		throw new IllegalArgumentException("ID invalido: " +cod);
	}

}
