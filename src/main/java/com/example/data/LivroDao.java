package com.example.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Autor;
import com.example.model.Livro;

public class LivroDao {

    private Connection conexao;

    public LivroDao(){
        try {
            conexao = ConnectionFactory.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserir(Livro livro) throws SQLException{
            var sql = "INSERT INTO TBL_LIVRO (livro, autor_id) VALUES (?, ?) ";
            var comando = conexao.prepareStatement(sql);
            comando.setString(1, livro.getLivro());
            comando.setLong(2, livro.getAutor().getId());

            comando.executeUpdate();


    }

    public List<Livro> listarTodos() throws SQLException{
         var comando = conexao.prepareStatement("SELECT tbl_livro.*, tbl_autor.nome, tbl_autor.email FROM TBL_LIVRO INNER JOIN tbl_autor ON tbl_livro.autor_id = tbl_autor.autor_id");
         var resultado = comando.executeQuery();
            
        var lista = new ArrayList<Livro>();

        while (resultado.next()) {
            lista.add(
                new Livro(
                    resultado.getLong("id"),
                    resultado.getString("livro"),
                    new Autor(
                        resultado.getLong("autor_id"),
                        resultado.getString("nome"),
                        resultado.getString("email"))
                )
            );
        }

        return lista;
    }

    public void apagar(Livro livro) throws SQLException {
        var comando = conexao.prepareStatement("DELETE FROM TBL_LIVRO WHERE id=?");
        comando.setLong(1, livro.getId());
        comando.executeUpdate(); 
    }

    public void atualizar(Livro livro) throws SQLException {
        var comando = conexao.prepareStatement("UPDATE TBL_LIVRO SET livro=? WHERE id=?");
        comando.setString(1, livro.getLivro());
        comando.setLong(2, livro.getId());
        comando.executeUpdate();
    }

    public void atualizarAutor(Object autor) {
    }

} 
