package com.iurylemos.cursomc.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.dto.CategoriaDTO;
import com.iurylemos.cursomc.dto.ClienteDTO;
import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.repositorios.ClienteRepositorio;
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
	
	
	public Cliente insert(Cliente obj) {
		//Progamação defensiva
		//Garantir que realmente estou inserindo um objeto novo.
		//Ou seja o objeto a ser inserido ele tem que ser nulo.
		obj.setId(null);
		return repo.save(obj);
	}
	
	//Metodo para atualização.
	
	public Cliente update(Cliente obj) {
		//Instanciar um cliente a partir do BancoDeDados.
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
		try {
			repo.delete(id);
		}catch(DataIntegrityViolationException e) {
			//Lançar uma excessão personalizada minha
			//Ou seja criar uma classe para tratar esse erro.
			//Só clonei a ObjetoNotFound..
			throw new DataIntegrityException("Não é possivel excluir um cliente há entidades relacionadas");
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
	
	
}
