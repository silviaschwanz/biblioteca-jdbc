package com.biblioteca.exception;

public class LivroInexistenteException extends RuntimeException {
    
    public LivroInexistenteException() {
        super("Livro não encontrado!");
    }
}
