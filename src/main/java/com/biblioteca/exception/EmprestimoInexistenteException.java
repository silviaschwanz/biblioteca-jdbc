package com.biblioteca.exception;

public class EmprestimoInexistenteException extends RuntimeException{
    
    public EmprestimoInexistenteException() {
        super("Emprestimo não encontrado!");
    }
}
