package com.iurylemos.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);

		/*
		 * Macete para executar a instaciação no momento em que a aplicação iniciar. vou
		 * botar para que a classe implemente CommandLineRunner = Permitir implementar
		 * um metodo auxiliar para executar alguma ação quando a aplicação iniciar.
		 */
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
