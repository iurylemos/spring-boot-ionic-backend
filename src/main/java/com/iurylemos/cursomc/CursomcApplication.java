package com.iurylemos.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iurylemos.cursomc.servicos.S3Servico;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	/*
	 * Testar o básico do S3 da Amazon aqui só para executar.
	 */
	
	@Autowired
	private S3Servico s3Servico;

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
		s3Servico.uploadFile("C:\\temp\\fotos\\restaurante.jpg");
		
	}

}
