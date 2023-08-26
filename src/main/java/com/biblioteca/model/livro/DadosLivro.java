package com.biblioteca.model.livro;

import java.time.LocalDate;

public record DadosLivro(Integer bibliotecaId, String titulo, LocalDate dataPublicacao, Long isbn, String situacao) {
    
}
