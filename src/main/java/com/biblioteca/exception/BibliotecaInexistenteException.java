package com.biblioteca.exception;

public class BibliotecaInexistenteException extends RuntimeException{
    
    public BibliotecaInexistenteException() {
        super("Biblioteca não encontrada!");
    }
}
