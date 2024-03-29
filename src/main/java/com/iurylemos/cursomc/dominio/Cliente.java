package com.iurylemos.cursomc.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iurylemos.cursomc.dominio.enums.Perfil;
import com.iurylemos.cursomc.dominio.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	/*
	 * Para garantir que o campo seja único, ou seja não tenha repetições
	 * basta colocar a anotação JPA que é a Column(unique=true)
	 * Isso faz o bd garantir que não haverá repetições nesse campo.
	 * Criar mais um teste em nossa validação customizada para não retornar 
	 * aquela excessão feia.. DataIngretityExce..
	 */
	@Column(unique=true)
	private String email;
	private String cpfOuCnpj;
	private Integer tipo;
	
	//Coloquei a anotação @JsonIgnore
	//Para não ficar aparecendo no Json quando recuperar um cliente
	//Não aparecer o bcript da senha
	//Não é bom incluir o campo senha quando vai recuperar os dados do sistema. 
	@JsonIgnore 
	private String senha;
	
	/*
	 * Modifiquei: private TipoCliente tipo;
	 * para private Integer tipo;
	 * Mas no construtor vai receber o dado no tipo cliente
	 * Ou seja: I
	 * Internamente o tipo do cliente vai ser amarzenado como um INTEIRO
	 * Porem:
	 * Para o mundo externo a classe vai expor um dado do TipoCliente
	 * 
	 * No construtor:
	 * tenho que colocar o tipo.getCod, ou seja quero só mente o código
	 * que está vindo com o argumento.
	 * Já que ele tbm tem a questão do outro parametro lá.
	 */
	
	//Associações.
	//No cliente tem vários endereços e vários telefones..
	//O vários endereço do lado Endereco já está mapeado o cliente
	//Então aqui basta eu utilizar o OneToMany e dizer onde está
	//Mapeado lá do outro lado que é campo de nome cliente.
	//O cliente pode serializar os endereço dele
	//Porem o endereço não pode serializar o cliente dele
	//Vou colocar o JsonManagerReference aqui
	//E na classe Endereço já boto a anotação que não pode
	
	//@JsonManagedReference
	//Na ultima atualizado foi retirado o JsonManagedReference.
	//Utilizando cascante para quando eu quiser apagar um cliente eu apague o endereco
	//Que são alinhados junto com eles
	//Pelo cascade e CascadeType, eu defino qual vai ser o comportado dessa ação.
	//Vou escolher ALL, ou seja toda operação que modificar o cliente
	//Vou poder refletir em cascata lá nos endereços
	//Ex: se eu for apagar o cliente então, vou apagar os endereços dele tbm automaticamente
	//Conclusão que quando tiver uma regra de negócio que você pode apagar em cascata
	//É só colocar isso aqui que o JPA já faz a regra no BD
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();

	/*
	 * No lugar de criar uma classe telefone que só tem um atributo
	 * vou criar uma lista, que diminui o código e fica melhor
	 * vou utilizar o Set que é uma coleção que não aceita
	 * repetições.
	 * 
	 * Para mapear e o JPA criar lá no banco como uma entidadeFraca
	 * Temos que colocar a anotação ElementCollection
	 * 
	 * E no outro coloco a tabela auxiliar que vai ter lá no BD
	 * relacional para guardar os telefones, e vou dar o nome
	 * dessa tabela de telefone.
	 */
	@ElementCollection
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();
	
	//Cliente de acordo com o PERFIL.
	//Garantir sempre que eu busque um cliente no BD 
	//Vai ser buscado os perfis também, por isso a anotação.
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	
	
	//Cliente conhece o pedido.
	//mappedBy foi mapeado pelo cliente que tem lá do outro lado
	//Não vou permitir que o cliente serialize os pedidos
	//Só o pedido serialize quem é o cliente.
	//@JsonBackReference
	//Onde tiver o JsonBackReference eu troco pelo @JsonIgnore
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	
	
	public Cliente() {
		//Todo usuário do meu sistema vai ter o perfil de CLIENTE
		//Alguns que eu determinar que vão ser admin também.
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		//Como esse campo não pode ser nulo, vou fazer uma condição aqui dentro
		//se o tipo for nulo vou atribuir nulo para esse campo, caso contrário
		//ai sim eu atribuio o tipo.getCodigo..
		//Operador ternário.
		//Para ele ou atribuir nulo ou atribuir o código caso o tipo seja nulo.
		this.tipo = (tipo==null) ? null : tipo.getCodigo();
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	//Macete do TipoCliente.
	public TipoCliente getTipo() {
		//Aqui dentro vou utilizar a validação
		//Que está lá na classe enumerada tipoCliente.
		//Testando se o código é veridico.
		//Vai ser baseado no tipo que é valor que tem dentro dessa CLASSE
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCodigo();
	}
	//Acima macete do TipoCliente
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<Perfil> getPerfis() {
		//Perfil é o tipo enumerado.
		//Dentro da coleção perfil eu estou guardando números inteiros.
		//Para converter eu uso o metodo toEnum da própria classe Perfil.
		//retornando os perfils do cliente
		//transformando em stream, e map
		//Para cada eleemento x dessa coleção
		//Vou converter para Inteiro e depois converto para lista
		//Ai fica uma lista de Set Inteiros.
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	//Adicionando perfil
	public void addPerfil(Perfil perfil) {
		//adicionando na minha lista os códigos pois são números inteiros
		perfis.add(perfil.getCodigo());
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

