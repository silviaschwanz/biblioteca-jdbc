package com.biblioteca.model.usuario;

public interface UsarioDAO {

    Usuario salva(DadosUsuario cadastroUsuario);

    void remove(String cpf);

    void altera(DadosUsuario cadastroUsuario);

    Usuario buscaPorCPF(String cpf);   
    
}
