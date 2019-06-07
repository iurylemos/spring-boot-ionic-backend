package com.iurylemos.cursomc.servicos.validacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.iurylemos.cursomc.dominio.Cliente;
import com.iurylemos.cursomc.dto.ClienteDTO;
import com.iurylemos.cursomc.recursos.exceptions.FieldMessage;
import com.iurylemos.cursomc.repositorios.ClienteRepositorio;

//Especificar o <NOMEDAANOTACAO e o tipodaclasse que vai aceitar a nossa anotação
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	/* PARA FAZER A BUSCA DE ID, VOU TER QUE INJETAR AQUI
	 * UM OBJETO HTTPSERVLETREQUEST POIS O ID VEM DIRETAMENTE NO LINK
	 * CLIENTES/{ID}
	 * OU SEJA VOU TER O PARAMETRO DA URI */
	
	@Autowired
	private HttpServletRequest request;
	
	
	
	
	@Autowired
	ClienteRepositorio repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
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
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		/*
		 * Primeiramente vou acessar o MAP que é uma estrutura de dados
		 * que é uma coleção de pares chave e valor.
		 * 
		 * Por que utilizar?
		 * Por que quando eu faço uma requisição ex: clientes/2 {2} é umq requisição
		 * e essa requisição pode ter varios atributos e esse atributos são guardados
		 * dentro de um MAP. parecido com a estrutura JSON vai ter uma chave e um valor
		 * e no caso eu tenho que pegar o "id" que e o valor que é 2.
		 * esse HandlerMapping pega um MAP de variaveis de URI que estão na requisição.
		 */
		@SuppressWarnings("unchecked") //Para retirar a mensagem amarela, tenho que colocar essa anotação.
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		//dentro do get coloco o atributo que eu quero, e depois passo para inteiro
		Integer uriId = Integer.parseInt(map.get("id"));
		
		
		//Instanciando uma lista vazia do tipo FieldMessage
		//É o tipo de carregar o nome do campo e messagem de erro desse campo.
		List<FieldMessage> list = new ArrayList<>();
		/**
		 * ERROS PERSONALIZADOS AQUI ABAIXO.
		 * VERIFICAR O EMAIL EXISTENTE, 
		 * POREM SE ESSE CLIENTE QUE CHEGOU É IGUAL O CLIENTE QUE ESTOU TENTANDO ATUALIZAR
		 * EU TENHO QUE DEIXAR PASSAR, NÃO POSSO DAR UM ERRO DE VALIDAÇÃO
		 * POR QUE É O MESMO CLIENTE, NÃO POSSAR DAR UM ERRO DE EMAIL.
		 * 
		 * VOU PRECISAR COMPARAR O ID DO CLIENTE QUE CHEGOU NA BUSCA DE EMAIL
		 * COM O ID DO CLIENTE QUE ESTOU ATUALIZANDO.
		 */
		
		
		
		
		/***
		 * Metodo para saber se o email do cliente já existe.
		 * E tentando se é o mesmo objDto que veio do banco de dados.
		 * 
		 * Vou testar se o id desse cliente é diferente do id que estou chamando
		 * para atualizar, ai sim vou dar a excessão.
		 * 
		 * Se isso no if acontecer significa que tentei atualizar o meu cliente
		 * contendo um email que já tinha em outro cliente que já tem no banco de dados.
		 * 
		 */
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(uriId)) {
			//Se for diferente de nulo significa que ele encontrou o registro no banco.
			//que tinha esse email aqui do objDto.
			//Nesse caso o e-mail que estou tentando inserir no objDto já existe.
			list.add(new FieldMessage("email", "E-mail já existe no banco de dados"));
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
