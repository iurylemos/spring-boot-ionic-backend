package com.iurylemos.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iurylemos.cursomc.dto.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	/*
	 * Se eu faço uma classe que herda a classe UsernamePassword...
	 * Automaticamente o Spring Security sabe que esse filtro
	 * vai ter que interceptar a requisição de login
	 * que vai ser o ENPOINT /login
	 * o /login já é reservado pelo SpringSecurity 
	 * 
	 * Aqui é muito boilerPlay, ou seja tem que fazer as coisas conforme o FRAMEWORK.
	 */
	
	//E também do AuthenticationManager que é do Spring Security
	private AuthenticationManager authenticationManager;
	
	//Injetar uma depedência do JWTUtil que é a classe.
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		
		try {
			//Metodo tente autenticar..
			//O que está implementado aqui dentro é BoilderPlayer total
			//Pega os dados da requisição /..
			//E transforma para CredenciaisDTO
			//Ou seja é coisa do framework para implementação.
			
			
			//Pegando os dados de email e senha
			CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
			//Instaciar um objeto do tipo UsernamePassword..
			//Não é o token do JWT isso é do Spring Security
			//Passando o email, senha e uma lista vázia.
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
			//De posse do outro objeto
			//Esse metodo vai verificar se esse usuário e senha são válidos
			//O framework faz isso com base no UserDetailsServicoImpl
			//O resultado disso ele retorna para o Authentication
			//e ai eu retorno ele.
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	//Metodo para autenticação com sucesso..
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		//Gerar um token e acrescentar isso na resposta da requisição.
		//getPrincipal vai retornar o usuário do Spring Security
		//Pego o .getUsername pegando o email da pessoa que fez o login
		//Ai faço um casting para o UserSS que implementei
		
		//E partir do username chamo o gererateToken passando esse username
		
		//O token vai ser retornado no resposta da requisição
		//Acrescetando ele no cabeçalho da requisição.
		
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		res.addHeader("Authorization", "Bearer " +token);
	}

}
