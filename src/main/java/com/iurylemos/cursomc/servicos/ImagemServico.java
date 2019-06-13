package com.iurylemos.cursomc.servicos;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iurylemos.cursomc.servicos.exceptions.FileException;

@Service
public class ImagemServico {
	/*
	 * Serviço responsável por fornecer funcionalidades de imagem
	 */
	
	public BufferedImage getJpgImagemParaArquivo(MultipartFile uploadFile) {
		//Pegar o multipartFile que é algum tipo de imagem png ou jpg..
		//E converte-lo para BufferedImagem que é JPG.
		//Esse FilenameUtis é de uma depedência do começo do capitulo
		//Para extrair nome extensão do arquivo facilmente
		//Depedência de nome commons-io.
		
		//1º Passo pegar a extensão do arquivo
		String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
		//2º Passo testar se a extensão não for png ou jpg vou rejeitar.
		if(!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}
		//Obtendo o BufferedImagem a apartir do uploadFile
		try {
			BufferedImage img = ImageIO.read(uploadFile.getInputStream());
			//Convertendo para jpg se for png.
			if("png".equals(ext)) {
				//utilizando o metodo que eu criei para converter para JPG
				img = pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	//Botei como público pois pode ser de utilidade para outra classe
	public BufferedImage pngToJpg(BufferedImage img) {
		//Conversão de png para jpg.
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		//Alguns png tem o fundo transparente e o color vai preencher com o BRANCO
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}
	
	public InputStream getInputStream(BufferedImage img, String extensao) {
		/*
		 * Função responsável por retornar um InputStream que é um objeto que
		 * encapsula leitura, a apartir de um BufferedImage
		 * 
		 * Precisamos desse metodo por que o metodo que faz o upload
		 * lá pro s3 ele recebe um tipo que é do InputStream
		 * e eu preciso obter um InputStream a apartir do BufferedImage
		 */
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extensao, os);
			return new ByteArrayInputStream(os.toByteArray());
		}catch(IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
		
	}
	
	/*
	 * Função para recortar a imagem, e deixa-la quadrada.
	 */
	
	public BufferedImage recortarImagem(BufferedImage sourceImg) {
		//Descobrindo qual o minimo? É altura ou largura?
		//SE ALTURA FOR MENOR QUE A LARGURA, ENTÃO É ALTURA, SE NÃO É LARGURA!
		int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
		//RECORTANDO A IMAGEM COM O CROP
		/*
		 * Agora que eu sei quem é o minimo
		 * Vou no meio da largura com o /2
		 * e boto menos a metade do mínimo.
		 * E assim eu faço com o altura
		 * Nos dois ultimos parametros eu informo quanto que eu quero recortar
		 * na altura e na largura, e assim eu coloco o minimo para as duas.
		 */
		return Scalr.crop(
				sourceImg, 
				(sourceImg.getWidth() /2) - (min/2), 
				(sourceImg.getHeight() /2) - (min/2),
				min, min);
	}
	
	//Função para redimensionar uma imagem!
	public BufferedImage redimensionar(BufferedImage sourceImg, int size) {
		//Recebe uma imagem e o tamanho que eu quero que seja recortada.
		//E no Scalr eu boto o máximo de qualidade.
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}

}
