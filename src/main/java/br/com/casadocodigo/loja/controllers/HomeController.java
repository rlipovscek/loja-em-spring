package br.com.casadocodigo.loja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	
	/*
	 *A anotação @RequestMapping tem a função de definir que o metodo atende a um determinado path ou endereço.
	 *Neste caso estamos definindo que o metodo index atenderá as requisições que chegarem na raiz do nosso projeto ("/") 
	 */
	@RequestMapping("/")
	public String index(){
		System.out.println("Entrando na home da CDC");
		return "home";
	}
}
