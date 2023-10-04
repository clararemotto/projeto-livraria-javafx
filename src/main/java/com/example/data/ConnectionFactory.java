package com.example.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    final static String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    final static String USER = "rm97898";
    final static String PASS = "210904";

    private static Connection conexao;

    public static Connection createConnection() throws SQLException{
        if (conexao == null){
            conexao = DriverManager.getConnection(URL, USER, PASS);
        }
        return conexao;
    }
    
}
