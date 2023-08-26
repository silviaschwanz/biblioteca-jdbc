package com.biblioteca.model.usuario;

import java.util.ArrayList;
import java.util.List;

import com.biblioteca.model.emprestimo.Emprestimo;

public class Usuario {

    private Integer id;
    private String cpf;
    private String nome;
    private List<Emprestimo> emprestimos = new ArrayList<>();


    public Usuario(Integer id, String cpf, String nome) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
    }
  
    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

     public Integer getId() {
        return id;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }   

    public List<Emprestimo> realizaEmprestimo(Emprestimo emprestimo){
        emprestimos.add(emprestimo);
        return emprestimos;
    }

    public List<Emprestimo> finalizaEmprestimo(Emprestimo emprestimo) {
        emprestimos.remove(emprestimo);
        return emprestimos;
    }
    
}
