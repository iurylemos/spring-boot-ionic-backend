package com.iurylemos.cursomc.recursos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static String decodeParam(String s) {
		//Para desencodar uma STRING utilizamos uma do próprio java que é URLDecoder
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//Caso der algum erro, retorne a minha função vazia.
			return "";
		}
	}

	/*
	 * Vou criar um metodo que pega da URL aquele 1,2,4 que é String
	 * e converta diretamente para inteiro para receber na classe ProdutoServico.
	 */
	
	public static List<Integer> decodeIntList(String s) {
		//Quebrar a string em vários pedaços e inserir na minha lista
		//String[] vet = s.split(",");
		//Vou criar uma lista de inteiros
		/*(List<Integer> list = new ArrayList<>();
		
		for(int i=0; i<vet.length; i++) {
			//Para cada posição do meu vetor vou converte-lo para inteiro.
			//E o resultado dessa conversão vou jogar dentro da minha lista de números inteiros.
			//Integer.parseInt(vet[i]);
			list.add(Integer.parseInt(vet[i]));
		}*/
		
		//PEGANDO TUDO ISSO ACIMA E FAZENDO EM UMA LINHA SÓ COM EXPRESSÃO LAMBDA!!!
		//SHOW DEMAIS!
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}
