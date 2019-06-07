package com.iurylemos.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iurylemos.cursomc.dominio.PagamentoComBoleto;
import com.iurylemos.cursomc.dominio.PagamentoComCartao;

/*
 * Classe de configuração.
 * Jackson pois vai configurar o nosso pacote JACKSON
 * É uma classe que vai ter algum metodo ou alguma informação que vai está disponivel
 * no meu sistema, e vai ser configurada no inicio da execução da aplicação.
 * 
 * Isso a gente informa com o @Configuration na CLASSE
 * 
 * E o @Bean naquele metodo que vai conter a informação de configuração.
 * 
 * É um código padrão que é exigência.
 */

@Configuration
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-
//without-hinting-the-pare
	
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				//O que vai mudar de projeto para projeto são as sub classe
				//que eu tenho que registrar no caso aqui só estou utilizando 
				//PagamentoComCartao e PagamentoComBoleto.
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}
