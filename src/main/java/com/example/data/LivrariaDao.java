package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Livraria;

public class LivrariaDao {

    final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    final String USER = "RM97898";
    final String PASS = "210904";

    public void inserir(Livraria livraria) throws SQLException{

        var conexao = DriverManager.getConnection(URL, USER, PASS);

        try {
            var sql = "INSERT INTO TBL_LIVRARIA(LIVRO, AUTOR) VALUES (?, ?) ";
            var comando = conexao.prepareStatement(sql);
            comando.setString(1, livraria.getLivro());
            comando.setString(2, livraria.getAutor());

            comando.executeUpdate();

            conexao.commit();
            
        } catch (Exception e) {
            conexao.rollback();
            throw e;
        } finally {
            conexao.close();
        }

    }

    public List<Livraria> listarTodos() throws SQLException{
        try (var conexao = DriverManager.getConnection(URL, USER, PASS);
         var comando = conexao.prepareStatement("SELECT * FROM TBL_LIVRARIA");
         var resultado = comando.executeQuery()) {
            
        var lista = new ArrayList<Livraria>();

        while (resultado.next()) {
            lista.add(new Livraria(
                resultado.getLong("id"),
                resultado.getString("livro"),
                resultado.getString("autor")
            ));
        }

        return lista;
    }
    }

    public void apagar(Livraria livraria) throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("DELETE FROM TBL_LIVRARIA WHERE id=?");
        comando.setLong(1, livraria.getId());
        comando.executeUpdate(); 
        conexao.close();
    }

    public void atualizar(Livraria livraria) throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("UPDATE TBL_LIVRARIA SET livro=?, autor=? WHERE id=?");
        comando.setString(1, livraria.getLivro());
        comando.setString(2, livraria.getAutor());
        comando.setLong(3, livraria.getId());
        comando.executeUpdate();
        conexao.close();
    }

} 
