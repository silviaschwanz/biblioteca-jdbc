package com.biblioteca.exception;

public class ListaDeLivrosVaziaException extends RuntimeException{
    
    
    public ListaDeLivrosVaziaException() {
        super("A lista de livros está vazia!");
    }
    
}
