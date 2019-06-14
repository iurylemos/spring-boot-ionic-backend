package com.iurylemos.cursomc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class FiltroExposicaoCabecalho implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//Não vou executar nada quando o filtro for iniciado
	}

	/*
	 * Esse é um filtro que vai interceptar todas as requisições
	 * ele vai expor o HEADER LOCATION na resposta
	 * e simplesmente ele encaminha com o chain 
	 * para o seu ciclo normal.
	 * Ai sim minha aplicação ANGULAR vai conseguir ler o meu cabeçalho
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//Expondo o cabeçalho personalizado
		//Transformando o ServletResponse que vem como parametro
		//Para HttpServletResponse e ai sim vou ter o addHeader.
		
		//Vou dar um casting para o response agora ser o res.
		/*
		 * Como o meu backend é um backend REST, então essa resposta
		 * ela é do tipo HttpServletResponse sem problema.
		 */
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("access-control-expose-headers", "location");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Não vou executar nada quando o filtro for destruido
	}

}
