package com.iurylemos.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		//Vamos precisar do userDetailsService
		//Por que esse filtro vai analisar o token, para saber se o token é valido
		//E para isso vou extrair o usuário lá da Requisição e buscar no BD
		//E sabe se existe mesmo.
		//Ou seja vou precisar do userDetailsService para fazer a busca por email.
		this.userDetailsService = userDetailsService;
	}
	
	//Implementar um metodo que intercepta a requisição do filtro
	
	/*
	 * Procedimento para liberar autorização do usuário
	 * que está tentando acessar o meu ENDPOINT /login
	 */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		//Pegar o valor do Authorization lá na requisição.
		//Que no caso é o do cabeçalho.
		//Filterchain = ObjetoDeCadeiaDeFiltrosDoSistema
		//O valor desse header começa com Bearer ESPAÇO..
		//Se isso for verdade significa que estou no caminho certo
		String header = request.getHeader("Authorization");
		if(header != null && header.startsWith("Bearer ")) {
			//No caso vou criar esse metodo getAuthentication
			//E no caso eu preciso pegar o valor que está depois do Bearer
			//Então vou utilizar o subString para cortar a String
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			//Se o token for diferente de nulo está tudo beleza.
			if(auth != null) {
				//liberar acesso ao filtro
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		//continuar a execução normal da requisição.
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		//Teste
		
		if(jwtUtil.tokenValido(token)) {
			//Pegar o usuário a partir do token
			String username = jwtUtil.getUsername(token);
			//De possa desse usuário eu tenho condição de instanciar
			//A classe grande UsernamePasswordAuthenticationToken
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}
