package br.com.casadocodigo.loja.controllers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private CarrinhoCompras carrinhoCompras;
	
	private CarrinhoItem criaItem(Integer id, TipoPreco tipo){
		Produto produto = produtoDao.find(id);
		CarrinhoItem carrinhoItem = new CarrinhoItem(tipo, produto);
		return carrinhoItem;
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView itens(){
		return new	ModelAndView("/carrinho/itens");	
	}
	
	
	@RequestMapping("/add")
	public ModelAndView add(Integer id, TipoPreco tipo){
		System.out.println("tipo no add "+ tipo +"id no add: "+ id+"\n\n\n");
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		CarrinhoItem carrinhoItem = criaItem(id,tipo);
		carrinhoCompras.add(carrinhoItem);
		return modelAndView;
	}
	
	@RequestMapping("/excluir")
	public ModelAndView excluir(Integer id, TipoPreco tipo){
		carrinhoCompras.remover(id, tipo);
		return new ModelAndView("redirect:/carrinho");
	}
}