package com.iurylemos.cursomc.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iurylemos.cursomc.dominio.Pedido;
import com.iurylemos.cursomc.repositorios.PedidoRepositorio;
import com.iurylemos.cursomc.servicos.exceptions.ObjetoNotFountException;

@Service
public class PedidoServico {
	//Operação capaz de buscar uma categoria por código.
	
	//Depedencia de quem puxa as informações do banco.
	//@Autowired essa dependência vai ser automaticamente instanciada pelo Spring
	//Pelo mecanismo de injeção de depedência ou inversão de controle.
	@Autowired
	private PedidoRepositorio repo;
	
	public Pedido find(Integer id) {
		//Implementar um servico que busca uma categoria.
		//Busco o ID e retorno ele.
		//findOne, se o Id existe, ele retorna, se não é nulo.
		
		Pedido obj = repo.findOne(id);
		if(obj == null ) {
			//Lançando uma excessão caso esse ID não exista.
			throw new ObjetoNotFountException("O objeto não encontrado! Id:" +id
				+ ", Tipo: " +Pedido.class.getName());
		}
		return obj;
	}
	

}
