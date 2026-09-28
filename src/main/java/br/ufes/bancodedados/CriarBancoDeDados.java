package br.ufes.bancodedados;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CriarBancoDeDados {
    public CriarBancoDeDados(){
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(tabelaCategoria());
            
        } catch (SQLException e) {
            System.out.println("Falha: " + e.getMessage());
        }
    }
    
    private String tabelaCategoria(){
        return """
               CREATE TABLE IF NOT EXISTS categoria (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    percentualDesconto DOUBLE NOT NULL
                );
            """;
    }
    
}
