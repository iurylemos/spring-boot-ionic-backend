package com.iurylemos.cursomc.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iurylemos.cursomc.dominio.Estado;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer> {
	/*
	 * Retornar todos os estados ordenados por nome.
	 */
	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
	

}
