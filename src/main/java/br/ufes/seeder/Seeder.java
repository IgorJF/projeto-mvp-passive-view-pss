package br.ufes.seeder;

import br.ufes.bancodedados.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Seeder {
    public void executar() {
        String sqlCategoria = "SELECT * FROM categoria";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlCategoria)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double percentualDesconto = rs.getDouble("percentualDesconto");

                System.out.println(id + " - " + nome + " - "
                        + percentualDesconto);
            }
        } catch (SQLException e) {
            System.out.println("Falha: " + e.getMessage());
        }
    }
}
