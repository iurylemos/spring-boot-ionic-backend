package com.iurylemos.cursomc.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Cidade;
import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.dominio.Endereco;
import com.iurylemos.cursomc.dominio.enums.TipoCliente;
import com.iurylemos.cursomc.dto.ClienteDTO;
import com.iurylemos.cursomc.dto.ClienteNewDTO;
import com.iurylemos.cursomc.repositorios.ClienteRepositorio;
import com.iurylemos.cursomc.repositorios.EnderecoRepositorio;
import com.iurylemos.cursomc.servicos.exceptions.DataIntegrityException;
import com.iurylemos.cursomc.servicos.exceptions.ObjetoNotFountException;

@Service
public class ClienteServico {
	//Operação capaz de buscar um cliente por código.
	
	//Depedencia de quem puxa as informações do banco.
	//@Autowired essa dependência vai ser automaticamente instanciada pelo Spring
	//Pelo mecanismo de injeção de depedência ou inversão de controle.
	@Autowired
	private ClienteRepositorio repo;
	
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;
	
	public Cliente find(Integer id) {
		//Implementar um servico que busca uma categoria.
		//Busco o ID e retorno ele.
		//findOne, se o Id existe, ele retorna, se não é nulo.
		
		Cliente obj = repo.findOne(id);
		if(obj == null ) {
			//Lançando uma excessão caso esse ID não exista.
			throw new ObjetoNotFountException("O objeto não encontrado! Id:" +id
				+ ", Tipo: " +Cliente.class.getName());
		}
		return obj;
	}
	
	//Metodo auxiliar.
	//a partir de um objeto DTO, vou construir um objeto Cliente.
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	//Sobrecarga do metodo acima para inserir um novo Cliente.
	public Cliente fromDTO(ClienteNewDTO objDto) {
		//No tipo tá como Inteiro mas na classe Cliente está como TipoCliente
		//Então vou ter que converter esse tipo Inteiro para TipoCliente
		//Vou utilizar o metodo que eu criei para conversão que é o toEnum
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		//Agora vou instanciar o Endereco.
		Endereco end = new Endereco(null, objDto.getLongadouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		
		//Incluir o endereco dentro da lista de enderecos do cliente
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	
	public Cliente insert(Cliente obj) {
		//Progamação defensiva
		//Garantir que realmente estou inserindo um objeto novo.
		//Ou seja o objeto a ser inserido ele tem que ser nulo.
		obj.setId(null);
		obj = repo.save(obj);
		//Para que o endereco apareça na lista quando eu estiver cadastrando um usuário
		//Preciso utilizar o enderecoRepositorio para salvar ele no bd
		//Para isso funcionar tenho que no fromDTO ter feito a associacao do cli.getEnd().add(end)
		//pois o metodo que utiliza o insert aqui instancia o fromDTO antes do INSERT.
		enderecoRepositorio.save(obj.getEnderecos());
		return obj;
	}
	
	//Metodo para atualização.
	
	public Cliente update(Cliente obj) {
		//Instanciar um cliente a partir do BancoDeDados
		//Pois esse objeto vai está monitorado pelo JPA
		//Depois eu pego esse objeto e atualizo esse objeto de acordo com o
		//que foi enviado pelo parametro.
		Cliente newObj = find(obj.getId());
		//Atualize os dados desse newObj com base no objeto que veio como argumento.
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	//Metodo delete.
	public void delete(Integer id) {
		//Verificar se o id existe
		find(id);
		//Colocar um tratamento aqui para caso queira apagar uma tabela integra.
		//Ou seja que possui produtos.
		//Quando um cliente possui produtos não pode apagar.
		//Só se tiver endereço pois utilizei o cascata.
		try {
			repo.delete(id);
		}catch(DataIntegrityViolationException e) {
			//Lançar uma excessão personalizada minha
			//Ou seja criar uma classe para tratar esse erro.
			//Só clonei a ObjetoNotFound..
			throw new DataIntegrityException("Não é possivel excluir um cliente há pedidos relacionadas");
			//Para receber isso lá na categoriaRecurso
			//Preciso modificar apenas na RecursoExceptionHandler.
			
		}
	}
	
	//Metodo trazer a lista de todos itens da categoria.
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	//Função para me retornar somente as categorias que eu quiser.
	//Paginação.
	//Vou utilizar uma classe chamada Page
	//Encapsula informações e operações sobre a paginação.
	//Dentro do parametro vou informar a pagina que eu quero
	//Entou coloco o Integer page.
	//E a outra informação vai ser a linhas por pagina eu quero.
	//E a outra vai ser por qual atributo eu quero ordernar.
	//O outro vai ser em qual direção eu quero ordenar
	//Se é descedente ou ascendente
	public Page<Cliente> findPage(Integer page, Integer linesPorPage, String orderBy, String direcao){
		//Para uma consulta para retornar uma pagina de dados
		//Tenho que criar um outro objeto do tipo PageRequest do Spring data
		//Ele vai ser um objeto que vai preparar aqui para minhas informações
		//Para que eu faça a consulta que me retorna a minha pagina de dados.
		//O utlimo que é properties é por onde eu quero ordenar.
		PageRequest pageRequest = new PageRequest(page, linesPorPage, Direction.valueOf(direcao), orderBy);
		return repo.findAll(pageRequest);
	}
	

	//Metodo auxiliar para atualização do obj que veio como argumento para o novo OBJ
	//Mas quero só atualizar o nome e email.
	//Atualize os dados desse newObj com base no objeto que veio como argumento.
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	//Casos de USO está abaixo e depois > Aula de nivelamento SQL e JPQL abaixo. 
	//Passo 11 : número é o código, tem que ser feito pelo backend
	//Passo 11 é para salvar o pedido a ser escolhido do carrinho.
	//Recebe esse pedido como argumento e retorna a instãncia monitorada do pedido inserido
	//Como estou recebendo o numéro que é codigoId, vou ter que salvar no banco de dados
	//Vou ter que projetar para salvar os pedidos no banco de dados
	//Passo 7 já está cotemplada pelo sistema que é o /clientes/{id}
	//Passo 4 e 5 faz parte do frontend.
	/**
	 * Metodo para realizar a busca pela lista paginada de produtos.
	 * 
	 * Recebe um nome e a lista de ids, e me retorna a lista paginada de produtos
	 * 
	 */
	//Passo 2 e 3 eu conseguir indetificar que estava faltando isso no meu sistema.
	
	
}
