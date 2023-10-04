package com.example.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Autor;

public class AutorDao {

    private Connection conexao;

    public AutorDao(){
        try{
            conexao = ConnectionFactory.createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Autor autor) throws SQLException{
        var sql = "INSERT INTO TBL_AUTOR (nome, email) VALUES (?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1, autor.getNome());
        comando.setString(2, autor.getEmail());
        comando.executeUpdate();
    }
    
    public List<Autor> listarTodos() throws SQLException{
        var comando = conexao.prepareStatement("SELECT * FROM TBL_AUTOR");
        var resultado = comando.executeQuery();

        var lista = new ArrayList<Autor>();

        while(resultado.next()){
            lista.add (
                new Autor(
                    resultado.getLong("id"), 
                    resultado.getString("nome"), 
                    resultado.getString("email")
                 )
            );
        }

        return lista;
    }

    public void apagar(Autor autor) throws SQLException{
        var comando = conexao.prepareStatement("DELETE FROM autores WHERE autor_id=?");
        comando.setLong(1, autor.getId());
        comando.executeUpdate();
    }

    public void atualizar(Autor autor) throws SQLException{
        var comando = conexao.prepareStatement("UPDATE TBL_AUTOR SET nome=?, email=? WHERE autor_id=?");
        comando.setString(1, autor.getNome());
        comando.setString(2, autor.getEmail());
        comando.setLong(3, autor.getId());
        comando.executeUpdate();
    }

}
