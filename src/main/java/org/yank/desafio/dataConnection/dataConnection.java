package org.yank.desafio.dataConnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dataConnection {
	private static final String URL_BD = "jdbc:sqlite:C:/SQLite/db/";

    public static void createNewDatabase(String fileName) {

        String url = URL_BD + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createNewTable() {
        String url = URL_BD+"ecommerce.db";
        
        String sql = 
        		"CREATE TABLE IF NOT EXISTS product(\n"
				+"	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
				+"	nome TEXT,\n"
				+"	url TEXT,\n"
				+"	categoria TEXT,\n"
				+"	cor TEXT,\n"
				+"	desconto INTEGER,\n"
				+"	classificacao INTEGER,\n"
				+"	preco REAL\n"
				+");";
        
        try (
        	Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
	 * Método responsável pela conexão com o BD
	 */
	 public Connection connect() {
	        String url = URL_BD+"ecommerce.db";
	        Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn;
	    }


    public static void main(String[] args) {
        createNewDatabase("ecommerce.db");
        createNewTable();
    }
}