package com.iurylemos.cursomc.servicos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Servico {

	/*
	 * Injetar a instancia do @Bean criado lá na S3Config como lá é do tipo
	 * AmazonS3, já injetei a depedência do mesmo tipo aqui
	 */

	private Logger LOG = org.slf4j.LoggerFactory.getLogger(S3Servico.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	/*
	 * Para receber o caminho na web retirei o String e vou colocar do tipo
	 * MultipartFile Esse tipo aqui é o tipo que o meu ENDPOINT vai receber E vai
	 * retornar uma URI Para retonar o endereço web do novo recurso que foi gerado
	 */
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			/*
			 * Esse metodo aqui vai ser responsável por fazer o upload do arquivo que eu
			 * passar o caminho, lá pro S3. Para isso eu preciso pegar o bucket, igual fiz a
			 * depedência acima
			 * 
			 * 
			 * Para enviar um arquivo lá pro S3 preciso instanciar um File que é do tipo
			 * io.File passando o caminho do arquivo que está no parametro do metodo
			 */
			// Pegando o nome do arquivo que vem pela requisição
			String fileNome = multipartFile.getOriginalFilename();
			// Instanciar um objeto básico de leitura InputStream do Java.io
			// Ele encapsula o objeto de leitura a partir de um arquivo de origem
			InputStream is = multipartFile.getInputStream();
			// Instanciar um String recebendo a informação do tipo do arquivo que foi
			// enviado
			// Afinal ele pode ser um img, um texto e etc..
			String contextType = multipartFile.getContentType();
			return uploadFile(is, fileNome, contextType);
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO:" + e.getMessage());
		}

	}
	

	// Sobrecarga do metodo acima, mas recebendo outros parametros.
	/*
	 * 1º Recebe um InputStream 2º Recebe o nome do arquivo a ser salvo no S3 3º
	 * Recebe o tipo do arquivo que vai ser salvo
	 */
	public URI uploadFile(InputStream is, String fileNome, String contextType) {

		try {
			/*
			 * Vou retirar o file pois ele vai procurar apartir do InputStream Vou utilizar
			 * outra versão do putObject uma que tem o InputStream. 1º é o nome do bucket 2º
			 * nome do aquivo 3º é o input 4º é o objectometadata
			 */

			ObjectMetadata meta = new ObjectMetadata();
			// A partir desse objeto eu seto o tipo de arquivo
			meta.setContentType(contextType);

			// File file = new File(localFilePath);

			LOG.info("Iniciando upload");
			/*
			 * s3client que é o objeto do AmazonS3 E utilizar o putObject do tipo Request.
			 * Ele recebe 3 parametros 1º bucket 2º Nome do arquivo 3º Caminho do arquivo
			 */
			s3client.putObject(new PutObjectRequest(bucketName, fileNome, is, meta));
			LOG.info("Upload finalizado");
			// Retornando a URI do objeto que foi gravado no S3

			return s3client.getUrl(bucketName, fileNome).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter" + e.getMessage());
		}

	}

}
