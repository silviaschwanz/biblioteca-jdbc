package com.biblioteca.exception;

public class UsuarioInexistenteException extends RuntimeException{

    public UsuarioInexistenteException() {
        super("Usuário não encontrado!");
    }
}
