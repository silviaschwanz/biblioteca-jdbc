package com.biblioteca.model.biblioteca;

import java.util.Map;

import com.biblioteca.model.livro.Livro;

public interface BibliotecaDAO {
    
    BibliotecaEntity salva(String nomeBiblioteca);

    void remove(String nomeBiblioteca);

    BibliotecaEntity buscaPorNome(String nomeBiblioteca);

    Map<Long, Livro> buscaLivrosAcervo(String nomeBiblioteca);

    Map<Long, Livro> buscaLivrosDisponiveis(String nomeBiblioteca);

}
