package com.iurylemos.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.iurylemos.cursomc.security.JWTAuthenticationFilter;
import com.iurylemos.cursomc.security.JWTAuthorizationFilter;
import com.iurylemos.cursomc.security.JWTUtil;
//Anotação para depois eu dizer quem pode acessar os meus endpoint @ENableGlobal
//Ai depois eu coloco lá no meu CategoriaRecurso os @PreAuthorize("hasAnyRole('ADMIN')")
//Para dizer que só quem vai ter acesso são os admin.

@Configuration //anotando como classe de configuração.

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Depedência para utilizar o banco H2 de teste
	@Autowired
	private Environment env;
	
	//Injeção do jwtUtil
	@Autowired
	private JWTUtil jwtUtil;
	
	/*
	 * O Framework é iteligente a ponto
	 * Eu injetei a interface o SPRING vai buscar no SISTEMA
	 * uma implementação dessa interface, e ele vai encontrar
	 * a UserDetailsServicoImpl e vai injetar uma instância aqui para gente
	 * essa instãncia é que vou utilizar para saber quem é o cara
	 * que é capaz de buscar um usuário por email.
	 */
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	
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
			"/categorias/**",
			"/estados/**"
	};
	
	/*
	 * VETOR PARA POST
	 * de usuários não logados, ou seja para aqueles que desejam se cadastrar..
	 * ENDPOINTS QUE PODEM SER ACESSADOS MESMO SEM ESTAR LOGADO
	 * esse /** é tipo dizer que tudo que tiver depois de cliente está liberado
	 * quando eu retiro, eu falo que ele só ter acesso ao /clientes
	 * retirei o /clientes/picture
	 * por que agora vou exigir que o usuário esteja logado (autenticado) 
	 * para adicionar sua foto, ou atualiza-la.
	 * 
	 */
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes",
			"/auth/forgot/**"
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
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		//Acrescentando o metodo criado lá no JWTAuthenticatio
		//Para tentativa de login.
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		//Colocando o filtro de autorização no meu Security
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		//Acrescentar a configuração abaixo
		//Que assegura que o nosso backend não cria a sessão de usuário.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	
	//Sobrecarga do metodo.
	//configure recebendo o Authnetication..
	//Esse ManagerBuilder chamei ele de auth
	//E apartir dele vou dizer quem é o userDetailsService
	//e no caso do passwordEncoder é bCryptPasswordEncoder
	//que já está definido aqui nessa classe.
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	
	
	
	
	
	
	
	/*
	 * Definindo um bean de CorsConfigurationSource
	 * e definindo um acesso básico de multiplasfontes para todos os caminhos
		Em outras palavras, estou permitindo o acesso aos meus endpoints /**
		por multiplasfontes com as configurações básicas. applyPertmirDefaultValues()..
		
		. Configuração inicial do Spring Security

	 * Acrescetando o PUT e DELETE no CORS tbm
	 */
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuracao = new CorsConfiguration().applyPermitDefaultValues();
		//Aqui abaixo eu coloco quais os tipos para o CORS acessar, inclusive o OPTIONS
		//Que é metodo que os frontEND utilizam para testar a primeira requisição.
		configuracao.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuracao);
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
