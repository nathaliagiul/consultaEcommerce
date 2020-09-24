package org.yank.desafio.produtoEntity;

public class Produto {
	String nome;
	String URL;
	String categoria;
	String cor;
	int desconto;
	int classificacao;
	Double preco;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getURL() {
		return URL;
	}
	
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getCor() {
		return cor;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public int getDesconto() {
		return desconto;
	}
	
	public void setDesconto(int desconto) {
		this.desconto = desconto;
	}
	
	public int getClassificacao() {
		return classificacao;
	}
	
	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
	}
	
	public Double getPreco() {
		return preco;
	}
	
	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
