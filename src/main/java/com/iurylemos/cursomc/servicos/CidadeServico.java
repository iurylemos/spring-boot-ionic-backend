package com.iurylemos.cursomc.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Cidade;
import com.iurylemos.cursomc.repositorios.CidadeRepositorio;

@Service
public class CidadeServico {
	// Operação capaz de buscar uma Cidade por código.

	// Depedencia de quem puxa as informações do banco.
	// @Autowired essa dependência vai ser automaticamente instanciada pelo Spring
	// Pelo mecanismo de injeção de depedência ou inversão de controle.
	@Autowired
	private CidadeRepositorio repo;

	// Metodo trazer a lista de todos itens da Cidade.
	public List<Cidade> findByEstado(Integer estadoId) {
		return repo.findCidades(estadoId);
	}

}
