package com.example.model;

public class Livro {

    private Long id;
    private String livro;
    private Autor autor;
    
    public Livro(Long id, String livro, Autor autor) {
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

    public Livro livro(String livro) {
        this.livro = livro;
        return this;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Livro autor(String newValue) {
        return null;
    }

}
