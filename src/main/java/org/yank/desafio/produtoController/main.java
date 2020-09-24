package org.yank.desafio.produtoController;

import java.io.IOException;
import java.text.ParseException;

import org.yank.desafio.produtoService.produtoService;

public class main {
	public static void main(String[] args) throws IOException, ParseException {
		produtoService service = new produtoService();
		
		System.out.println("Coletando os dados");
		service.consultaProdutos();
		
		System.out.println("\nProduto mais barato");
		service.consultaProdutoMaisBarato();
		
		System.out.println("\nProduto mais avaliado");
		service.consultaProdutoMaisAvaliado();
		
		System.out.println("\nProduto com maior desconto");
		service.consultaProdutoMaiorDesconto();
		
		System.out.println("\nTodos os produtos");
		service.listProdutos();
	}
}
