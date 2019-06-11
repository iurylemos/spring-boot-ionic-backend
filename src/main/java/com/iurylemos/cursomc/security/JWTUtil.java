package com.iurylemos.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
}
