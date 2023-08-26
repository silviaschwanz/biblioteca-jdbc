package com.biblioteca.model.livro;

import java.time.LocalDate;

import com.biblioteca.model.biblioteca.Biblioteca;


public class Livro {

    private Integer id;  
    private String titulo;
    private LocalDate dataPubicacao;
    private Long isbn;
    private SituacaoLivro situacao;
 

    public Livro(Integer id, String titulo, LocalDate dataPubicacao, Long isbn) {
        this.id = id;
        this.titulo = titulo;
        this.dataPubicacao = dataPubicacao;
        this.isbn = isbn;
        this.situacao = SituacaoLivro.DISPONIVEL;
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getDataPubicacao() {
        return dataPubicacao;
    }

    public Long getIsbn() {
        return isbn;
    }

    public SituacaoLivro getSituacao() {
        return situacao;
    }

    public void emprestar() {
        this.situacao = SituacaoLivro.EMPRESTADO;
    }   

    public void devolver() {
        this.situacao = SituacaoLivro.DISPONIVEL;
    }
    
}
