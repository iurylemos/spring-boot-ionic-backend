package com.iurylemos.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.iurylemos.cursomc.dominio.enums.Perfil;

public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	
	//Lista de perfis
	//Do tipo Collection igual o SpringSecurity exige..
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS() {
		
	}
	
	/*
	 * Vou fazer um macete para nesse contrutor eu receber uma lista de Perfis
	 * do tipo set e dentro dele eu realizo a conversão
	 */
	
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		//Coloco o descrição pois lá dentro da classe Perfil ele vai precisar da DESCRICAO
		//Que é aquela parte do ROLE_CLIENTE, ROLE_ADMIN..
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}




	public Integer getId() {
		return id;
	}
	
	

	/*
	 * Esse contrato tem todos esses metodos.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	/*
	 * Essa consta não está expirada?
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//A consta não está bloqueada?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//As credênciais não estão expiradas?
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//Usuário está ativo?
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
