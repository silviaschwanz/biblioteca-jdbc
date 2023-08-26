package com.biblioteca.model.emprestimo;

import java.time.LocalDate;
import java.util.Set;

public record EmprestimoEntity(Integer id, Integer usuarioId, LocalDate dataRetirada, LocalDate dataDevolucao, Boolean finalizado, Set<Integer> livrosEmprestados) {
    
}
