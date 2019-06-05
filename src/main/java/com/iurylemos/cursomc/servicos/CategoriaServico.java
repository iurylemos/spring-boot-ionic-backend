package com.iurylemos.cursomc.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.repositorios.CategoriaRepositorio;
import com.iurylemos.cursomc.servicos.exceptions.DataIntegrityException;
import com.iurylemos.cursomc.servicos.exceptions.ObjetoNotFountException;

@Service
public class CategoriaServico {
	//Operação capaz de buscar uma categoria por código.
	
	//Depedencia de quem puxa as informações do banco.
	//@Autowired essa dependência vai ser automaticamente instanciada pelo Spring
	//Pelo mecanismo de injeção de depedência ou inversão de controle.
	@Autowired
	private CategoriaRepositorio repo;
	
	public Categoria find(Integer id) {
		//Implementar um servico que busca uma categoria.
		//Busco o ID e retorno ele.
		//findOne, se o Id existe, ele retorna, se não é nulo.
		
		Categoria obj = repo.findOne(id);
		if(obj == null ) {
			//Lançando uma excessão caso esse ID não exista.
			throw new ObjetoNotFountException("O objeto não encontrado! Id:" +id
				+ ", Tipo: " +Categoria.class.getName());
		}
		return obj;
	}
	
	//Metodo para inserir
	
	public Categoria insert(Categoria obj) {
		//Progamação defensiva
		//Garantir que realmente estou inserindo um objeto novo.
		//Ou seja o objeto a ser inserido ele tem que ser nulo.
		obj.setId(null);
		return repo.save(obj);
	}
	
	//Metodo para atualização.
	
	public Categoria update(Categoria obj) {
		//Diferença do insert para cá
		//É justamente a programação defensiva o insert só vai inserir quando o id é nulo.
		//Verificar se o id existe, utilizando o buscar.
		find(obj.getId());
		return repo.save(obj);
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
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
			//Para receber isso lá na categoriaRecurso
			//Preciso modificar apenas na RecursoExceptionHandler.
			
		}
	}

}
