package com.iurylemos.cursomc.servicos;

import java.io.File;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Servico {
	
	/*
	 * Injetar a instancia do @Bean criado lá na S3Config
	 * como lá é do tipo AmazonS3, já injetei a depedência do mesmo tipo aqui
	 */
	
	private Logger LOG = org.slf4j.LoggerFactory.getLogger(S3Servico.class);
	
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public void uploadFile(String localFilePath) {
		/*
		 * Esse metodo aqui vai ser responsável por fazer o upload
		 * do arquivo que eu passar o caminho, lá pro S3.
		 * Para isso eu preciso pegar o bucket, igual fiz a depedência acima
		 * 
		 * 
		 * Para enviar um arquivo lá pro S3
		 * preciso instanciar um File que é do tipo io.File
		 * passando o caminho do arquivo que está no parametro do metodo
		 */
		try {
			File file = new File(localFilePath);
			LOG.info("Iniciando upload");
			/*
			 * s3client que é o objeto do AmazonS3
			 * E utilizar o putObject do tipo Request.
			 * Ele recebe 3 parametros
			 * 1º bucket
			 * 2º Nome do arquivo
			 * 3º Caminho do arquivo
			 */
			s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
			LOG.info("Upload finalizado");
		}catch(AmazonServiceException e) {
			/*
			 * Pode ser que eu passe usuario e senha errado
			 * Acontecer alguma coisa no servidor quando eu tentar enviar
			 * o arquivo por aqui.
			 * E o erro é um logger, e vou apresentar o erro abaixo.
			 */
			LOG.info("AmazonServiceException: " +e.getErrorMessage());
			/*Log.info no status Code também
			 * Que foi o status de error, por que é um erro de servidor
			 * ele me devolve o status do HTTP também.  */
			LOG.info("Status code: " +e.getErrorCode());
		}catch(AmazonClientException e) {
			/*
			 * Outra excessão que pode acontecer quando enviarmos o arquivo.
			 */
			LOG.info("AmazonClienteException: " +e.getMessage());
		}
	}

}
