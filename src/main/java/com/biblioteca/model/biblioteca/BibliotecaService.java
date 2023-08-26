package com.biblioteca.model.biblioteca;

import com.biblioteca.ConnectionService;
import com.biblioteca.model.usuario.DadosUsuario;
import com.biblioteca.model.usuario.Usuario;
import com.biblioteca.model.usuario.UsuarioDAOMsql;

public class BibliotecaService {

    private ConnectionService connectionService;

    public BibliotecaService() {
        this.connectionService = new ConnectionService();
    }

    public Biblioteca cadastraBiblioteca(String nomeBiblioteca) {

    }

    public Usuario cadastraUsuario(DadosUsuario dadosUsuario) {
        Usuario usuario = new UsuarioDAOMsql(connectionService.openConnection()).salva(dadosUsuario);    
        connectionService.closeConnection();
        return usuario;
    }


    
    
}
