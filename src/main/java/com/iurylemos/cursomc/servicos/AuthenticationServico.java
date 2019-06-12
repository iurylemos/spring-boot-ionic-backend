package com.iurylemos.cursomc.servicos;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.repositorios.ClienteRepositorio;
import com.iurylemos.cursomc.servicos.exceptions.ObjetoNotFountException;

@Service //Para a classe ser Componente do framework
public class AuthenticationServico {
	
	//Injeção
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailServico emailServico;
	
	//Para gerar caracteres aleatórios
	private Random random = new Random();
	
	public void enviarNovaSenha(String email) {
		//Verificar se esse email existe
		Cliente cliente = clienteRepositorio.findByEmail(email);
		if(cliente == null) {
			throw new ObjetoNotFountException("Email não encontrado");
		}
		
		String newSenha = novaSenha();
		//Vou salvar a senha do cliente
		//Como ela é salva com o Bcript, tenho que utilizado na injeção.
		
		cliente.setSenha(pe.encode(newSenha));
		//Salvando o cliente no banco
		clienteRepositorio.save(cliente);
		//Enviar o email para esse cliente e para isso uso o EmailServico
		emailServico.enviarNovaSenhaEmail(cliente, newSenha);
		
	}

	private String novaSenha() {
		/*
		 * Gerar uma senha aleatório
		 * de 10 caracteres
		 * e esses caracteres podem ser digitos ou letras.
		 * 
		 * Criar um vetor
		 */
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			//para cada valor de i vai receber o randomChar
			// é uma função que recebe um caractere aleatório
			vet[i] = randomChar();
		}
		//Vai retonar um String a partir do vetor.
		return new String(vet);
	}

	private char randomChar() {
		//1º vou querer gerar um digito letra maiuscula ou minuscula.
		//Coloquei o 3, para gerar um número inteiro de 0 até 2
		//0 o código é 48. e o 9 é 57
		int opt = random.nextInt(3);
		if(opt == 0) { //Gera um digito
			//retorne um número aleatório de 0 a 9
			//Dentro do random coloco 10.
			//E o 48 que é o 0
			//E assim eu gero um caractere com dígito de 0 a 9.
			return (char) (random.nextInt(10) + 48);
			
		}else if (opt == 1) { //Gera letra maisucula
			//Como letras são 26 letras
			//65 é o código A
			return (char) (random.nextInt(26) + 65);
		}else { //Gera letra minuscula.
			return (char) (random.nextInt(26) + 97);
		}

	}

}
