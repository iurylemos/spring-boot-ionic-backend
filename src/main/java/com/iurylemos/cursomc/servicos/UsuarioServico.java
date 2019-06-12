package com.iurylemos.cursomc.servicos;

import org.springframework.security.core.context.SecurityContextHolder;

import com.iurylemos.cursomc.security.UserSS;

public class UsuarioServico {
	
	public static UserSS authenticated() {
	/*
	 * Esse metodo vai servir para saber quem é o usuário que está logado.
	 * Retorna o Usuário do Spring Security
	 * E esse UserSS
	 * Ele tem id, email, senha e os authorities que são os perfis.
	 * Para obtermos quem é o usuário logado na forma de UserSS
	 * existe um metodo para isso
	 * que é o SecurityContextHolder.getContext().getAuthentication().getPrincipal()
	 * porém pode dar uma excecao, um exemplo é de um usuário não logado.
	 * O tipo que vai retornar, não é de userSS, ai então ele dar uma excessao na hora 
	 * que eu dou um casting para UserSS
	 * então para proteger coloco ele dentro do try.
	 */
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch (Exception e) {
			return null;
		}
	}
}
