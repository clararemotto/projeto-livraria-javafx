package com.example.model;

public class Livraria {

    private Long id;
    private String livro;
    private String autor;
    
    public Livraria(Long id, String livro, String autor) {
        this.id = id;
        this.livro = livro;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Livraria livro(String livro) {
        this.livro = livro;
        return this;
    }

    public Livraria autor(String autor) {
        this.autor = autor;
        return this;
    }

}
