package com.iurylemos.cursomc.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.repositorios.ClienteRepositorio;
import com.iurylemos.cursomc.security.UserSS;

@Service
public class UserDetailsServicoImpl implements UserDetailsService {
	
	//Injeção
	@Autowired
	private ClienteRepositorio repo;

	//Metodo que vai receber o usuário e vai retornar o UserDetails
	//Que é tipo que eu acabei de implementar lá no UserSS
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//Buscar por email o Cliente.
		
		//Busco o cliente do banco de dados
		Cliente cli1 = repo.findByEmail(email);
		//se esse cliente que eu busquei for igual a nulo, significa que não existe
		if(cli1 == null) {
			//lanço uma excessão aqui do SpringSecurity
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(cli1.getId(), cli1.getEmail(), cli1.getSenha(), cli1.getPerfis());
	}

}
