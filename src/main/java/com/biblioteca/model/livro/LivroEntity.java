package com.biblioteca.model.livro;

import java.time.LocalDate;

public record LivroEntity(Integer id, Integer bibliotecaId, String titulo, LocalDate dataPublicacao, Long isbn, String situacao) {
    
}
