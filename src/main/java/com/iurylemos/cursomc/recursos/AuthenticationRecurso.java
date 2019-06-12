package com.iurylemos.cursomc.recursos;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iurylemos.cursomc.dto.EmailDTO;
import com.iurylemos.cursomc.security.JWTUtil;
import com.iurylemos.cursomc.security.UserSS;
import com.iurylemos.cursomc.servicos.AuthenticationServico;
import com.iurylemos.cursomc.servicos.UsuarioServico;

@RestController
@RequestMapping(value="/auth")
public class AuthenticationRecurso {
	/*
	 * Vai ser um novo controladorRest responsável por disponibilizar ENDPOINTS
	 * relacionados a autorização e autheticação. Caminho dele: /auth
	 */
	
	//JwtUTIL
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthenticationServico servico;
	
	

	/*
	 * Vai ter o endpoint que vai ser /auth/refresh_token
	 * E esse endpoint é protegido por auteticação
	 * pois só pode acessar se estiver logado.
	 * 
	 * Passou no filtro de autorização ele entra no metodo:
	 * PEGO O USUÁRIO LOGADO COM O METODO AUTHETICATED
	 * e gero um token novo com o meu usuário
	 * e com isso a data de expiração vai ser renovado
	 * e adiciono esse token na resposta da requisição
	 * e retorno o responseEntity
	 */
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UsuarioServico.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Metodo de esqueci minha senha.
	 * Ele vai ter que receber o email que a pessoa informar lá.
	 * Como é um POST vou dar a preferência para receber esse email
	 * na forma de @ Request Body e @ Valid para validar as anotações
	 * que tem lá no atributo
	 * e para isso vou criar uma classe de auxilio que é a DTO
	 */
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forget(@Valid @RequestBody EmailDTO objDto) {
		//chamar o metodo de servico para enviar a nova senha
		servico.enviarNovaSenha(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}

}
