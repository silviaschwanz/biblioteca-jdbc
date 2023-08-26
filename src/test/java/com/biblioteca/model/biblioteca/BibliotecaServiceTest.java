package com.biblioteca.model.biblioteca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.biblioteca.model.usuario.DadosUsuario;

public class BibliotecaServiceTest {

    private BibliotecaService bibliotecaService;

    @BeforeEach
    public void setUp() {
        bibliotecaService = new BibliotecaService();
        
    }


    @Test
    public void givenUsuarioDto_whenCadastraUsuario_thenReturnUsuario() {
        

        DadosUsuario dadosUsuario = new DadosUsuario("Silverio das Couves", "02305456232", 1);
        
        
        BibliotecaService bibliotecaService = new BibliotecaService();
        //bibliotecaService.
    }
}
