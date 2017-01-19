package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.Produto;

/*
 * Classe responsavel pela validação dos produtos de nossa aplicação 
 * 
 */

public class ProdutoValitation implements Validator {

	public void validar(Produto produto, Errors errors) {

	}

	/*
	 * Indica qual a classe receberá o suporte desta implementação. Com isso o
	 * Spring pode verificar se o objeto é uma instância daquela classe ou não.
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		/*
		 * ferramenta de validação de dados do spring, ela recebe o erro, o
		 * campo a ser verificado e o que deve ser verificado dentro deste campo
		 */
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");

		// Verificamos se o número de páginas é menor que zero

		Produto produto = (Produto) target;
		if (produto.getPaginas() <= 0) {

			/*
			 * informamos que deve-se verificar paginas e checar se ele foi
			 * preenchido
			 */
			errors.rejectValue("paginas", "field.required");

		}
	}

}
