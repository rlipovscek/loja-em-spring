package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

public class CarrinhoItem {

	private TipoPreco tipo;
	private Produto produto;
	
	public CarrinhoItem(TipoPreco tipo, Produto produto){
		this.tipo=tipo;
		this.produto=produto;
	}

	public TipoPreco getTipo() {
		return tipo;
	}

	public void setTipo(TipoPreco tipo) {
		this.tipo = tipo;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	
	public BigDecimal getPreco(){
		return produto.precoPara(this.tipo);
	}
	
	public BigDecimal getTotal(int quantidadeItens) {
		return this.getPreco().multiply(new BigDecimal(quantidadeItens));
	}

	
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarrinhoItem other = (CarrinhoItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	
}
