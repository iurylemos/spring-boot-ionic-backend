package com.iurylemos.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.iurylemos.cursomc.servicos.validacao.ClienteInsert;

//Vou criar uma anotação customizada.
//Vou chama-la de @ClienteInsert
//Ela não vai ser uma anotação do campo, vai ser uma anotação da classe

@ClienteInsert
public class ClienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	//Classe para servir quando eu for cadastrar um cliente.
	
	@NotEmpty(message="Preenchimento obrigatorio")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatorio")
	@Email(message="E-mail invalido") 
	private String email;
	
	//Se fosse só cpf ou cpnj, tinha uma anotação para as duas @CPF, @CPNJ
	@NotEmpty(message="Preenchimento obrigatorio")
	private String cpfOuCnpj;
	
	private Integer tipo;
	
	//Senha
	@NotEmpty(message="Preenchimento obrigatorio")
	private String senha;
	
	//Dados do endereco
	@NotEmpty(message="Preenchimento obrigatorio")
	private String longadouro;
	
	@NotEmpty(message="Preenchimento obrigatorio")
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	@NotEmpty(message="Preenchimento obrigatorio")
	private String cep;
	//Criando o telefone1
	@NotEmpty(message="Preenchimento obrigatorio")
	private String telefone1;
	private String telefone2;
	private String telefone3;
	//Codigo da cidade
	private Integer cidadeId;
	
	public ClienteNewDTO() {
		
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLongadouro() {
		return longadouro;
	}

	public void setLongadouro(String longadouro) {
		this.longadouro = longadouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
