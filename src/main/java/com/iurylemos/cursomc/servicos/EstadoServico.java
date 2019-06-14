package com.iurylemos.cursomc.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Estado;
import com.iurylemos.cursomc.repositorios.EstadoRepositorio;

@Service
public class EstadoServico {
	// Operação capaz de buscar uma categoria por código.

	// Depedencia de quem puxa as informações do banco.
	// @Autowired essa dependência vai ser automaticamente instanciada pelo Spring
	// Pelo mecanismo de injeção de depedência ou inversão de controle.
	@Autowired
	private EstadoRepositorio repo;

	public List<Estado> findAll() {

		return repo.findAllByOrderByNome();
	}

}
