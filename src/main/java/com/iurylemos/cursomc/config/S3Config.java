package com.iurylemos.cursomc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
	/*
	 * Classe para configuração do AmazonS3
	 * Essa class tem que ter uma anotação do tipo @Bean
	 * do tipo AmazonS3
	 */
	
	/*
	 * Em cada valor abaixo vou colocar as chaves referentes aos mesmo
	 * igual no application.properties
	 */
	
	@Value("${aws.access_key_id}")
	private String awsId;
	
	@Value("${aws.secret_access_key}")
	private String awsKey;
	
	@Value("${s3.region}")
	private String region;
	
	
	
	
	@Bean
	public AmazonS3 s3client() {
		//Instanciação é padrão da API da AMAZON
		//Criar um objeto do tipo BasicAWSCredentiais passando o ID e senha
		//Do usuário que tem lá no arquivo application.properties
		BasicAWSCredentials awsCred = new BasicAWSCredentials(awsId, awsKey);
		//Instanciando um objeto do tipo AmazonS3 e passando a região
		//Região está no application.properties = s3.region=??
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCred)).build();
		return s3client;
		
	}
	
	
	

}
