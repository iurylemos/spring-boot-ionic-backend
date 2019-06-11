package com.iurylemos.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * Componente para que ela possar ser injetada em outras classes como Componente.
 */
@Component
public class JWTUtil {

	//Gerar o Token.
	/*
	 * 1º Do Secret para embaralhar junto com o token
	 * 2º Do tempo de expiracao.
	 */
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiracao;
	
	//
	public String generateToken(String username) {
		/*
		 * Vamos utilizar aquele depedência que foi colacada lá no pom.xml
		 * em respeito ao TOKEN
		 * 
		 * Jwts.builder = gera um token
		 * vou chamar os metodos dele.
		 * setSubject = usuário
		 * setExpiration = BASEADO NO TEMPO de expiração
		 * vai ser o horário atual do sistema + tempodeExpiracao.
		 * .sigWith = COMO VOU ASSINAR MEU TOKEN
		 * 1º Parametro: QUAL ALGORITMO DE ASSINATURA VOU UTILIZAR
		 * 2º Parametro: QUAL O SEGREDO preciso colocar o .getBytes
		 * pois o metodo aqui pega um array de bytes.
		 * 
		 */
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiracao))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	public boolean tokenValido(String token) {
		//O CLAIMS É UM TIPO DO JWT QUE AMARZENA AS REIDIVIAÇÕES DO TOKEN
		//TRADUÇÃO DO CLAIMS É REIVINDIAÇÕES
		//NO NOSSO CASO É O USUÁRIO E O TEMPO DE EXPIRAÇÃO
		Claims claims = getClaims(token);
		if(claims != null) {
			//Pegando o nome do usuário
			String username = claims.getSubject();
			//Data de expiração.
			Date dataExpiracao = claims.getExpiration();
			Date agora = new Date(System.currentTimeMillis());
			/*
			 * Se a data de agora for anterior a data de Expiração
			 * Nesse caso vai estar válido
			 */
			if(username != null && dataExpiracao != null && agora.before(dataExpiracao)) {
				return true;
			}
		}
		return false;
	}
	//FUNÇÃO  PARA OBTER O CLAINS A PARTIR DO TOKEN.

	private Claims getClaims(String token) {
		/*
		 * Política:
		 * Tentou pegar os claims do token, o token era invalido ou deu algum problema
		 * eu retorno nulo.
		 */
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}catch(Exception e) {
			return null;
		}
		
	}
	
	//Pegar um usuário a partir do Token
	public String getUsername(String token) {
		//O CLAIMS É UM TIPO DO JWT QUE AMARZENA AS REIDIVIAÇÕES DO TOKEN
		//TRADUÇÃO DO CLAIMS É REIVINDIAÇÕES
		//NO NOSSO CASO É O USUÁRIO E O TEMPO DE EXPIRAÇÃO
		Claims claims = getClaims(token);
		if(claims != null) {
			return claims.getSubject();
		}
		return null;
	}
}
