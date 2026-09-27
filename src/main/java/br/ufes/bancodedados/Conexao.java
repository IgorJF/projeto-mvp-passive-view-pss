package br.ufes.bancodedados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:sqlite:my.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
