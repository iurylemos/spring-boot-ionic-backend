package com.iurylemos.cursomc.dominio.enums;

public enum TipoCliente {
	
	/*
	 * Não é legal gravar na forma de String pois ocupa mais espaço.
	 * é melhor INT, tipo 0 para FISICA, e 1 para JURIDICA
	 * 
	 * Melhor fazer um controle manual de qual código número
	 * corresponde a cada valor.
	 * 
	 * Coloquei esse controle e tive que colocar o construtor
	 * Para tipo imprimir o codigo e a descricao bonitinho..
	 * 
	 * Quero um código atribuido a cada valor da ENUMERAÇÃO
	 * e a descricao.
	 */

	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	
	//Construtor de tipo enumerado ele é PRIVATE
	private TipoCliente(int codigo, String descricao) {
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
	public static TipoCliente toEnum(Integer cod) {
		//Programação defensiva
		if(cod == null ) {
			return null;
		}
		//todos objetos x no tipo cliente
		for(TipoCliente x : TipoCliente.values()) {
			//Se o codigo que estou verificando for igual ao codigo estou procurando
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		//Se esgotar e não for ninguém, ai eu lanço uma excessao.
		throw new IllegalArgumentException("ID invalido: " +cod);
	}
}
