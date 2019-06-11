package com.iurylemos.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration //anotando como classe de configuração.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Depedência para utilizar o banco H2 de teste
	@Autowired
	private Environment env;
	
	//Definir um vetor de String 
	//para dizer quais os caminhos por padrão vão estar libeirados.
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};
	
	/*
	 * Um usuário que não tá logado, e que não é administrador
	 * ele pode recuperar os produtos categorias, pode visualizar os catálogos
	 * de produtos lá, mas de maneira alguma ele pode alterar, inserir, deletar..
	 * 
	 * Vou criar outro vetor com os caminhos, somente de leitura
	 * ou seja, que posso recuperar.
	 * Vão ser os caminhos em que eu só vou permitir recuperar os dados..
	 */
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**"
	};
	
	
	

	//Sobreescrever um metodo do WebSecutiryConfigurerAdapter
	/*
	 * Dentro desse metodo eu acesso o http e chamo o metodo authorizeRequests()
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Acessando o banco H2
		/*
		 * Pegando os profile ativo do projeto
		 * se no profile ativo eu estiver no "test" significa que quero
		 * acessar o h2
		 */
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		
		
		/*
		 * esse metodo cors ele faz o seguinte:
		 * Se tiver um CorsConfigurationSource definido, 
		 * que é o metodo que tem abaixo..
		 * então as configurações desse metodo vão ser aplicadas
		 * chamando o metodo cors();
		 * 
		 * Caso a gente queira que requisições de multiplas fontes
		 * sejam feitas ao nosso backend, temos que fazer isso expliciatamente.
		 * isso é necessário
		 */
		//configurar que o nosso backend vai desabilitar a proteção de ataque a CSRF
		//como nosso sistemas é stateless, de modo geral não precisamos se preocupar
		//com esse ataque que é baseado no amarzenamento da autenticação e sessão
		//E como não amarzenamos sessão não precisamos nos preocupar.
		http.cors().and().csrf().disable();
		//Todos os caminhos que estiver nesse vetor eu vou permitir.
		//Quando eu coloco o anyRequest eu digo
		//Para todo o resto eu exigo autenticação.
		//Acrescentei na minha configuração mais um VETOR
		//Só que agora vão ser separados
		//Vou colocar o HttpMethod.GET do lado dele
		//Para indicar que só vou permitir o metodo GET
		//Os cara que estiverem no meu VETOR.
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		//Acrescentar a configuração abaixo
		//Que assegura que o nosso backend não cria a sessão de usuário.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	/*
	 * Definindo um bean de CorsConfigurationSource
	 * e definindo um acesso básico de multiplasfontes para todos os caminhos
		Em outras palavras, estou permitindo o acesso aos meus endpoints /**
		por multiplasfontes com as configurações básicas. applyPertmirDefaultValues()..
		
		. Configuração inicial do Spring Security

	 * 
	 */
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		//Metodo para criptografar senha.
		//O meu sistema vai ter disponível na forma de Bean
		//Um componente BCryptPasswordEncoder, que vou poder injetar em qualquer classe.
		//Do meu Sistema.
		return new BCryptPasswordEncoder();
	}
}
