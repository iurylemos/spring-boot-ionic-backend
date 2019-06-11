package com.iurylemos.cursomc.dominio.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int codigo;
	private String descricao;
	
	private Perfil(int codigo, String descricao) {
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
	public static Perfil toEnum(Integer cod) {
		//Programação defensiva
		if(cod == null ) {
			return null;
		}
		//todos objetos x no tipo cliente
		for(Perfil x : Perfil.values()) {
			//Se o codigo que estou verificando for igual ao codigo estou procurando
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		//Se esgotar e não for ninguém, ai eu lanço uma excessao.
		throw new IllegalArgumentException("ID invalido: " +cod);
	}

}
