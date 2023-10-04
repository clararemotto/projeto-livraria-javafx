package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.AutorDao;
import com.example.data.LivroDao;
import com.example.model.Autor;
import com.example.model.Livro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class PrimaryController implements Initializable {

    @FXML private TextField txtLivro;
    @FXML private ComboBox<Autor> cboAutor;
    
    @FXML TableView<Livro> tabelaLivro;    
    @FXML TableColumn<Livro, String> colLivro;
    @FXML TableColumn<Livro, String> colAutor;
    
    @FXML private TextField txtAutor;
    @FXML private TextField txtEmail;
    
    @FXML TableView<Autor> tabelaAutor;
    @FXML TableColumn<Autor, String> colNome;
    @FXML TableColumn<Autor, String> colEmail;

    private LivroDao livroDao = new LivroDao();
    private AutorDao autorDao = new AutorDao();


    public void salvarLivro(){
        var livro = new Livro(
            null, 
            txtLivro.getText(), 
            cboAutor.getSelectionModel()
                    .getSelectedItem()
        );

        try{
            livroDao.inserir(livro);
            tabelaLivro.getItems().add(livro);

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void salvarAutor(){

        var autor = new Autor(
            null, 
            txtAutor.getText(), 
            txtEmail.getText()
        );

        try{
            autorDao.inserir(autor);
            tabelaAutor.getItems().add(autor);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    private void consultarLivro() {
        try {
            livroDao.listarTodos().forEach(livro -> tabelaLivro.getItems().add(livro));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Não foi possivel carregar os dados do banco");
        }
    }

    private void consultarAutor() {
        try {
            autorDao.listarTodos().forEach(autor -> tabelaAutor.getItems().add(autor));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Não foi possivel carregar os dados do banco");
        }
    }

    public void apagarLivro(){
        if(!confimarExclusao()) return;            

        try {
            var livroSelecionado = tabelaLivro.getSelectionModel().getSelectedItem();
            if(livroSelecionado == null){
                mostrarMensagemDeErro("Você deve selecionar um livro para apagar");
                return;
            }
            livroDao.apagar(livroSelecionado);
            tabelaLivro.getItems().remove(livroSelecionado);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao apagar o livro do banco de dados");
        }
    }

    public void apagarAutor(){
        if(!confimarExclusao()) return;            

        try {
            var autorSelecionado = tabelaAutor.getSelectionModel().getSelectedItem();
            if(autorSelecionado == null){
                mostrarMensagemDeErro("Você deve selecionar um autor para apagar");
                return;
            }
            autorDao.apagar(autorSelecionado);
            tabelaAutor.getItems().remove(autorSelecionado);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao apagar o autor do banco de dados");
        }
    }

    private boolean confimarExclusao() {
        var alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Atenção");
        alert.setContentText("Tem certeza que deseja apagar o item selecionado? Essa ação não poderá ser desfeita.");
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

    public void carregarComboBoxAutor() {
        try {
            cboAutor.getItems().addAll(autorDao.listarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao carregar dados dos autores!");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colLivro.setCellValueFactory(new PropertyValueFactory<>("livro"));
        colLivro.setCellFactory(TextFieldTableCell.forTableColumn());
        colLivro.setOnEditCommit(event -> {
            atualizarLivro(event.getRowValue().livro(event.getNewValue()));
        });

        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAutor.setCellFactory(TextFieldTableCell.forTableColumn());
        colAutor.setOnEditCommit(event -> {
            atualizarLivro(event.getRowValue().livro(event.getNewValue()));
        });

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        // colAutor.setOnEditCommit(event -> {
        //     atualizarAutor(event.getRowValue().autor(event.getNewValue()));
        // });

        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());

        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        tabelaLivro.setEditable(true);
        tabelaAutor.setEditable(true);

        consultarLivro();
        consultarAutor();
        carregarComboBoxAutor();
    }

    private void atualizarAutor(Autor autor) {
        try {
            autorDao.atualizar(autor);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao atualizar dados do livro");
        }
    }

    private void atualizarLivro(Livro livro) {
        try {
            livroDao.atualizar(livro);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao atualizar dados do livro");
        }
    }

}
