package com.biblioteca.exception;

public class CreateConnectionException extends RuntimeException{


    public CreateConnectionException(String error) {

        super("Erro de conexão com o banco de dados. - Erro Original: " + error);
    }

}
