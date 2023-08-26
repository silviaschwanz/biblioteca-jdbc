package com.biblioteca.exception;

public class UsuarioJaExisteExcpetion extends RuntimeException {
    
    public UsuarioJaExisteExcpetion() {
        super("Usário já cadastrado!");
    }

}
