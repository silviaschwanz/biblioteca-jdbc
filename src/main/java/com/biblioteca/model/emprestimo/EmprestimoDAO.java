package com.biblioteca.model.emprestimo;

import java.time.LocalDate;
import java.util.Set;

import com.biblioteca.model.livro.Livro;

public interface EmprestimoDAO {

    EmprestimoEntity salva(Emprestimo emprestimo);

    EmprestimoEntity finalizaEmprestimo(EmprestimoEntity emprestimo);

    EmprestimoEntity buscaEmprestimoPorDataAndUsuario(LocalDate dataRetirada, Integer usuarioId);

    Set<Livro> buscaLivrosDoEmprestimo(LocalDate dataRetirada, Integer usuarioId);

}
