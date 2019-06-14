package com.iurylemos.cursomc.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iurylemos.cursomc.dominio.Cidade;

@Repository
public interface CidadeRepositorio extends JpaRepository<Cidade, Integer> {
	//interface capaz de acessar o BD
	//Ela herda o JpaRepository
	
	//Fazer a query para implementar a consulta
	//SELECIONE obj.estado.id é igual ao estado que vou passar no parametro
	//Para dizer que o estado_id do parametro é o mesmo que estadoId
	//Tive que colocar o @Param.
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estadoId")Integer estado_id);
	
	

}
