package com.iurylemos.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iurylemos.cursomc.dominio.Categoria;
import com.iurylemos.cursomc.repositorios.CategoriaRepositorio;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	//Depedência
	@Autowired
	CategoriaRepositorio categoriaRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
		
		/*
		 * Macete para executar a instaciação no momento em
		 * que a aplicação iniciar.
		 * vou botar para que a classe implemente
		 * CommandLineRunner = Permitir implementar um metodo
		 * auxiliar para executar alguma ação
		 * quando a aplicação iniciar.
		 */
	}

	@Override
	public void run(String... args) throws Exception {
		//Instaciação dos meus objetos.
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		//salvar
		categoriaRepositorio.save(Arrays.asList(cat1, cat2));
		
	}

}
