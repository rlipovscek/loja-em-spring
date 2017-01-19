package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValitation;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	// variavel que chama a classe que salva nossos arquivos
	@Autowired
	private FileSaver fileSaver; 
	
	/*
	 * informa que o Spring vai instanciar a classe, o ProdutoDAO deve ter
	 *  a anotação @Repository para funcionar
	 */
	@Autowired 
	private ProdutoDAO produtoDAO;

	
	/*
	 * Responsável por fazer o bind entre o produto usado na função gravar e 
	 * a classe ProdutoValidation, que faz a validação dos produtos
	 */
	@InitBinder
	public void InitBinder(WebDataBinder binder){
		binder.addValidators(new ProdutoValitation());
		
	}
	
	
	
	/*
	 * retorna um model and view que exporta para o jsp um objeto com os tipos
	 * de preços usados pelo programa
	 */
	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView mv = new ModelAndView("produtos/form");
		mv.addObject("tipos", TipoPreco.values());
		return mv;

	}// fim de form

	
	/*
	 * grava os dados obtidos no formulário de inserção de produtos, chamado em
	 * form, no banco de dados 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario,@Valid Produto produto,BindingResult result
				,RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()){
			return form(produto); // Se o produto possuir erros o sistema retorna para o formulário
		}

		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		
		produtoDAO.gravar(produto);
		redirectAttributes.addFlashAttribute("sucesso","produto cadastrado com sucesso");
		
		
		return new ModelAndView("redirect:produtos");
	}// fim de gravar
	
	
	/*
	 * Responsavel por chamar a jsp que lista os produtos cadastrados no sistema
	 * */
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView listar(){
		ModelAndView modelAndView = new ModelAndView("/produtos/lista");
		List<Produto> produtos = produtoDAO.getLista();
		modelAndView.addObject("produtos",produtos);
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("/produtos/detalhe");
		Produto produto = produtoDAO.find(id);
		modelAndView.addObject("produto",produto);
		return modelAndView;
	}
	
	
	

} // fim da classe ProdutoController
