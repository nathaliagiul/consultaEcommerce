package org.yank.desafio.produtoRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.yank.desafio.produtoEntity.Produto;

public class produtoRepository {
	
	/**
	 * Método responsável pela conexão com o BD
	 */
	 private Connection connect() {
	        String url = "jdbc:sqlite:D:/SQLite/db/ecommerce.db";
	        Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn;
	    }

	 /**
	 * Método responsável por inserir todos os produtos encontrados
	 */
	public void inserirProduto(Produto produto) {
		String sql = 
				"INSERT INTO product(nome,url,categoria,cor,desconto,classificacao,preco) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getURL());
            pstmt.setString(3, produto.getCategoria());
            pstmt.setString(4, produto.getCor());
            pstmt.setInt(5, produto.getDesconto());
            pstmt.setInt(6, produto.getClassificacao());
            pstmt.setDouble(7, produto.getPreco());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	/**
	 * Método responsável por realizar a consulta e buscar o produto mais barato
	 */
	public void consultaProdutoMaisBarato() {
		String sql = "SELECT * FROM product WHERE preco > 0.0 GROUP BY preco ORDER BY preco ASC limit 1;";
		
		try (Connection conn = this.connect();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            
	            while (rs.next()) {
	                System.out.println("Produto: "+rs.getString("nome") +  "\n" +
			         				   "URL: "+rs.getString("url") +  "\n" +
			         				   "Categoria: "+rs.getString("categoria") +  "\n" +
			         				   "Cor: "+rs.getString("cor") +  "\n" +
			                           "Desconto: "+rs.getInt("desconto")+"%"  +  "\n" +
			                           "Classificacao: "+rs.getInt("classificacao") +  "\n" +
			                           "Preço: R$"+rs.getDouble("preco")
			                          );
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	}

	/**
	 * Método responsável por realizar a consulta e buscar o produto mais avaliado
	 */
	public void consultaProdutoMaisAvaliado() {
		String sql = "SELECT *, max(classificacao) FROM product GROUP BY classificacao ORDER BY classificacao DESC limit 1;";
		
		try (Connection conn = this.connect();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            
	            while (rs.next()) {
	                System.out.println("Produto: "+rs.getString("nome") +  "\n" +
			         				   "URL: "+rs.getString("url") +  "\n" +
			         				   "Categoria: "+rs.getString("categoria") +  "\n" +
			         				   "Cor: "+rs.getString("cor") +  "\n" +
			                           "Desconto: "+rs.getInt("desconto")+"%"  +  "\n" +
			                           "Classificacao: "+rs.getInt("classificacao") +  "\n" +
			                           "Preço: R$"+rs.getDouble("preco")
			                          );
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	}

	/**
	 * Método responsável por realizar a consulta e buscar o produto com maior desconto
	 */
	public void consultaProdutoMaiorDesconto() {
		String sql = "SELECT * FROM product GROUP BY desconto ORDER BY desconto DESC limit 1;";
		
		try (Connection conn = this.connect();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            
	            while (rs.next()) {
	                System.out.println("Produto: "+rs.getString("nome") +  "\n" +
	                				   "URL: "+rs.getString("url") +  "\n" +
	                				   "Categoria: "+rs.getString("categoria") +  "\n" +
	                				   "Cor: "+rs.getString("cor") +  "\n" +
	                                   "Desconto: "+rs.getInt("desconto")+"%" +  "\n" +
	                                   "Classificacao: "+rs.getInt("classificacao") +  "\n" +
	                                   "Preço: R$"+rs.getDouble("preco")
	                                   );
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	}
}
