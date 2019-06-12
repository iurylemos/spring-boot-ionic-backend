package com.iurylemos.cursomc.repositorios;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.dominio.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> {
	//interface capaz de acessar o BD
	//Ela herda o JpaRepository
	/*
	 * Que é um tipo especial do SPRING
	 * capaz de acessar os dados com base em um tipo que você 
	 * passar.
	 * 
	 * e no 2º parametro qual o tipo do atributo identificador do OBJ
	 * no caso como o id é integer.
	 * 
	 */
	
	/**
	 * Consulta para retornar os pedidos do Cliente
	 * findByCliente = BuscarPedidosPorCliente
	 * Vou colocar a anotação do transaction para reduzir o LOOK
	 * E na camada de serviço é que eu faço a implantação desse metodo
	 * Lá é onde tem a regra de negócio
	 * Quem é o usuário logado, e buscar os pedidos só desse usuário.
	 */
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
	

}
