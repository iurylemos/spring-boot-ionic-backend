package com.iurylemos.cursomc.servicos.validacao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.iurylemos.cursomc.dominio.enums.TipoCliente;
import com.iurylemos.cursomc.dto.ClienteNewDTO;
import com.iurylemos.cursomc.recursos.exceptions.FieldMessage;
import com.iurylemos.cursomc.servicos.validacao.utils.BR;

//Especificar o <NOMEDAANOTACAO e o tipodaclasse que vai aceitar a nossa anotação
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}

	/*
	 * Esse metodo aqui é o metodo da ConstraintValidator
	 * que verifica se o nosso tipo, vai ser valido ou não
	 * ele retorna true se for valido e false se não for valido
	 * O verdadeiro ou falso que for retornado aqui no isValid
	 * vai ser percebido na anotação @Valid
	 * Esse @Valid tbm vai depender desse metodo aqui.
	 * O metodo initialize tbm é do ConstraintValidator
	 * nesse metodo podemos colocar alguma programação de inicialização
	 * mas não vai precisar aqui, ele fica vázio mesmo.
	 */
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		//Instanciando uma lista vazia do tipo FieldMessage
		//É o tipo de carregar o nome do campo e messagem de erro desse campo.
		List<FieldMessage> list = new ArrayList<>();
		/**
		 * ERROS PERSONALIZADOS AQUI ABAIXO.
		 */
		//Validação de CPF
		
		/*
		 * Se o tipo do DTO for igual a pessoaFISICA, tenho que colocar o getCodigo
		 * Pois o DTO está carregando o numero inteiro
		 * && se o CPF dele não for valido
		 */
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj","CPF invalido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj","CNPJ invalido"));
		}
		
		
		
		//Pecorrer a minha lista de FieldMessage vou adicionar a cada objeto
		//E inserindo pegando a mensagem de erro com o e.getMessage
		//e o Campo onde está o erro com o e.getNomeCampo.
		//Esse dois comandos que tem dentro dele são diretamente do FRAMEWORK, 
		// é ele quem cria esses comandos que no caso tem a função de transportar
		//os meus erros personalizados acima para a lista de eros do framework
		//Onde essa lista de erros do framework vai ser tratada e gerado a resposta?
		//É lá no meu Exception..Handler
		//Só estamos acrescentando nessa lista os erros personalizados do FieldMessage.
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getCampoNome())
					.addConstraintViolation();
		}
		//Se a minha lista de erros estiver vazia é por que não obteve nenhum erro.
		//Então o meu metodo isValid vai retornar verdadeiro
		
		return list.isEmpty();
	}
}
