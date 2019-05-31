package com.iurylemos.cursomc.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.repositorios.CategoriaRepositorio;

@Service
public class CategoriaServico {
	//Operação capaz de buscar uma categoria por código.
	
	//Depedencia de quem puxa as informações do banco.
	//@Autowired essa dependência vai ser automaticamente instanciada pelo Spring
	//Pelo mecanismo de injeção de depedência ou inversão de controle.
	@Autowired
	private CategoriaRepositorio repo;
	
	public Categoria buscar(Integer id) {
		//Implementar um servico que busca uma categoria.
		
		Categoria obj = repo.findOne(id);
		return obj;
	}
	

}
