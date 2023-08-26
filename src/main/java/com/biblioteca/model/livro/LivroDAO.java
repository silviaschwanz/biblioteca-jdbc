package com.biblioteca.model.livro;

public interface LivroDAO {

    LivroEntity salva(DadosLivro livro);

    void remove(Long isbn);

    LivroEntity altera(LivroEntity livroEntity);

    LivroEntity buscaPorIsbn(Long isbn);

    LivroEntity buscaPorTitulo(String titulo);
    
}
