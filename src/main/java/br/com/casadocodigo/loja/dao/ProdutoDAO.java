package br.com.casadocodigo.loja.dao;

import java.util.List;

/*
 * Classe responsavel pela inserçãao de produtos em nosso banco de dados
 * 
 * */

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Produto;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext // Informamos que o objeto vai persistir os dados
	private EntityManager manager; // Criamos um gerenciador de entidades

	/*
	 * Função que grava os produtos no banco de dados
	 */
	public void gravar(Produto produto) {
		manager.persist(produto);// informamos que o dado a ser gardado é o
									// objeto produto
	}// fim de gravar

	/*
	 * Função responsável por listar os produtos de nosso banco de dados e
	 * exportar uma lista com estes itens
	 */
	public List<Produto> getLista() {
		return manager.createQuery("select p from Produto p").getResultList();
	} // fim de get list

	public Produto find(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos precos where p.id = :id",
				Produto.class).setParameter("id", id).getSingleResult();
	}

} // fim de ProdutoDAO
