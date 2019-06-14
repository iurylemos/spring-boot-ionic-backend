package com.iurylemos.cursomc.recursos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iurylemos.cursomc.dominio.Cidade;
import com.iurylemos.cursomc.dominio.Estado;
import com.iurylemos.cursomc.dto.CidadeDTO;
import com.iurylemos.cursomc.dto.EstadoDTO;
import com.iurylemos.cursomc.servicos.CidadeServico;
import com.iurylemos.cursomc.servicos.EstadoServico;

@RestController
@RequestMapping(value = "/estados")
public class EstadoRecurso {

	// Depedência
	@Autowired
	private EstadoServico servico;
	
	@Autowired
	private CidadeServico cidadeServico;

	// Metodo de mostrar todas Estados
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = servico.findAll();

		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);
	}
	
	/*
	 * Por está fortemente vinculado, não preciso criar um CidadeRecurso, 
	 * posso criar o endpoint aqui mesmo das cidades aqui mesmo.
	 */
	
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
		
		//Passando o estadoId que vier na URL vai me retornar uma lista de cidades
		List<Cidade> list = cidadeServico.findByEstado(estadoId);
		
		//Retornando uma lista de DTO.
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		//Retorno a lista de DTO.
		return ResponseEntity.ok().body(listDto);
	}

}
