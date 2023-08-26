package com.biblioteca.model.emprestimo;

import java.time.LocalDate;
import java.util.Set;

import com.biblioteca.model.livro.Livro;

public record DadosEmprestimo(Integer usuarioId, LocalDate dataRetirada, Set<Livro> livros) {
    
}
