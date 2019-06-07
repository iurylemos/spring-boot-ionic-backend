package com.iurylemos.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.servicos.validacao.ClienteUpdate;

//Anotação personalizada
@ClienteUpdate 
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ClientedeAtualizacao.
	//Essa classe vai ter os dados básicos para eu ATUALIZAR,DELETAR, E LISTAR OS CLIENTES
	
	//O cliente tem ID, NOME e EMAIL, CPNJouCPF.
	//O cliente não pode mduar nunca o CPNJouCPF.
	
	private Integer id;
	
	
	@NotEmpty(message="Preenchimento obrigatorio")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatorio")
	@Email(message="E-mail invalido")  //Validação de e-mail, verifica se tem o @ tudo direitinho.
	private String email;
	
	public ClienteDTO() {
		
	}
	
	//Construtor recebendo uma entidade Cliente e gera o meu DTO.
	
	public ClienteDTO(Cliente obj) {
		//Responsável por instanciar o meu DTO.
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
