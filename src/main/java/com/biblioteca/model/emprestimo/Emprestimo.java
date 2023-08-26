package com.biblioteca.model.emprestimo;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import com.biblioteca.model.livro.Livro;
import com.biblioteca.model.usuario.Usuario;

public class Emprestimo {
 
    private Integer id;
    private Usuario usuario;
    private Set<Livro> livros = new HashSet<>();
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private Boolean finalizado;


    public Emprestimo(Integer id, Usuario usuario, Set<Livro> livros, LocalDate dataRetirada) {
        this.usuario = usuario;
        this.livros = livros;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao =  dataRetirada.plusDays(7);
        this.finalizado = false;
    }

    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }       
    
    public Long verificaAtraso() {
        LocalDate diaDaEntrega = LocalDate.now();
        Period period = Period.between(dataRetirada, diaDaEntrega);
        int diasDeAtraso = period.getDays();
        if(diasDeAtraso != 0 && diasDeAtraso <= 7) {
            return (long) 0;
        }
        return (long) diasDeAtraso;
    }

    public Long calculaMulta() {
        Long diasDeAtraso = verificaAtraso();
        if(diasDeAtraso != 0) {
            return (long) (diasDeAtraso * 10);

        }
        return (long) 0;        
    }

    public void finalizaEmprestimo() {
        this.finalizado = true;
    }
    
}
