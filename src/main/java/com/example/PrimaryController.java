package com.example;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.LivrariaDao;
import com.example.model.Livraria;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class PrimaryController implements Initializable {

    @FXML private TextField txtLivro;
    @FXML private TextField txtAutor;
    
    @FXML TableView<Livraria> tabela;

    @FXML TableColumn<Livraria, String> colLivro;
    @FXML TableColumn<Livraria, String> colAutor;
    
    private LivrariaDao livrariaDao = new LivrariaDao();

    public void salvar(){

            var livraria = new Livraria(
            null,
            txtLivro.getText(),
            txtAutor.getText()
        );

        try{
            livrariaDao.inserir(livraria);
            tabela.getItems().add(livraria);

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    private void consultar() {
        try {
            livrariaDao.listarTodos().forEach(livraria -> tabela.getItems().add(livraria));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Não foi possivel carregar os dados do banco");
        }
    }

    public void apagar(){
        if(!confimarExclusao()) return;            


        try {
            var livrariaSelecionado = tabela.getSelectionModel().getSelectedItem();
            if(livrariaSelecionado == null){
                mostrarMensagemDeErro("Você deve selecionar um livro para apagar");
                return;
            }
            livrariaDao.apagar(livrariaSelecionado);
            tabela.getItems().remove(livrariaSelecionado);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao apagar o livro do banco de dados");
        }
    }

    private boolean confimarExclusao() {
        var alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Atenção!");
        alert.setContentText("Tem certeza que deseja apagar o livro selecionado? Essa ação não poderá ser desfeita.");
        return alert.showAndWait()
                .get()
                .getButtonData()
                .equals(ButtonData.OK_DONE);
    }

    private void mostrarMensagemDeErro(String mensagem) {
        var alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Erro");
        alert.setContentText(mensagem);
        alert.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colLivro.setCellValueFactory(new PropertyValueFactory<>("livro"));
        colLivro.setCellFactory(TextFieldTableCell.forTableColumn());
        colLivro.setOnEditCommit(event -> {
            atualizar(event.getRowValue().livro(event.getNewValue()));
        });

        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAutor.setCellFactory(TextFieldTableCell.forTableColumn());
        colAutor.setOnEditCommit(event -> {
            atualizar(event.getRowValue().autor(event.getNewValue()));
        });

        tabela.setEditable(true);

        consultar();
    }

    private void atualizar(Livraria livraria) {
        try {
            livrariaDao.atualizar(livraria);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao atualizar dados do livro");
        }
    }
}
