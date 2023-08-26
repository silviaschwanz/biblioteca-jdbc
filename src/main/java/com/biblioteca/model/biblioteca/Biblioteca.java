package com.biblioteca.model.biblioteca;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.biblioteca.exception.ListaDeLivrosVaziaException;
import com.biblioteca.exception.UsuarioInexistenteException;
import com.biblioteca.exception.UsuarioJaExisteExcpetion;
import com.biblioteca.model.emprestimo.Emprestimo;
import com.biblioteca.model.livro.Livro;
import com.biblioteca.model.usuario.Usuario;

public class Biblioteca {

    private Integer id;
 
    private String nome;

    private Map<Long, Livro> livrosAcervo = new HashMap<>();

    private Map<Long, Livro> livrosDisponiveis = new HashMap<>();

    private Map<String, Usuario> usuarios = new HashMap<>();


    public Biblioteca(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Map<Long, Livro> getLivrosAcervo() {
        return livrosAcervo;
    }


    public Map<Long, Livro> getLivrosDisponiveis() {
        return livrosDisponiveis;
    }


    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario cadastraUsuario(Usuario usuario) {

        Optional<Usuario> usuarioEncontrado = buscaUsuario(usuario.getCpf());

        if(usuarioEncontrado.isPresent()){
            throw new UsuarioJaExisteExcpetion();
        }        
        usuarios.put(usuario.getCpf(), usuario);
        return buscaUsuario(usuario.getCpf()).get();
    }

    public Optional<Usuario> buscaUsuario(String cpf) {
        return Optional.ofNullable(usuarios.get(cpf));
    }


    public Livro cadastraLivro(Livro livro) {
        livrosAcervo.put(livro.getIsbn(), livro);
        livrosDisponiveis.put(livro.getIsbn(), livro);
        return livro;
    }

    
    public Map<Long, Livro> removeLivro(Livro livro) {
        livrosAcervo.remove(livro.getIsbn());
        livrosDisponiveis.remove(livro.getIsbn());
        return livrosAcervo;
    }


    public List<Livro> buscaLivrosDisponiveisPeloTitulo(String titulo) {

        Predicate<Livro> filtro = livro -> livro.getTitulo().equals(titulo);
        return livrosDisponiveis.values().stream().filter(filtro).collect(Collectors.toList());
    }



    public Emprestimo emprestaLivros(Usuario usuario, List<String> livrosSolicitados) {
        Optional<Usuario> usuarioCadastrado = buscaUsuario(usuario.getCpf());
        
        if(!usuarioCadastrado.isPresent()){
            throw new UsuarioInexistenteException();
        }
        
        if(livrosSolicitados.isEmpty()){
            throw new ListaDeLivrosVaziaException();
        }
      
        Set<Livro> livrosEmprestados = registraLivrosEmprestados(livrosSolicitados);      
        Emprestimo emprestimo = new Emprestimo(usuario, livrosEmprestados, LocalDate.now());
        usuario.realizaEmprestimo(emprestimo);

        return emprestimo;
    }



    private Set<Livro> registraLivrosEmprestados(List<String> titulos) {
        
        Set<Livro> livrosEmprestados = new HashSet<>();

        for (String titulo : titulos) {
            List<Livro> livrosEncontrados = buscaLivrosDisponiveisPeloTitulo(titulo);

            Optional<Livro> livroDisponivel = livrosEncontrados.stream().findFirst();
            
            if(livroDisponivel.isPresent()){
            livroDisponivel.get().emprestar();
            livrosDisponiveis.remove(livroDisponivel.get().getIsbn());
            livrosEmprestados.add(livroDisponivel.get());
            }            
            
        }

        return livrosEmprestados;        
    }


    public Usuario finalizaEmprestimo(Emprestimo emprestimo) {
        emprestimo.calculaMulta();
        registraDevolucaoDeLivros(emprestimo.getLivros());
        Usuario usuario = emprestimo.getUsuario();
        usuario.finalizaEmprestimo(emprestimo);
        return usuario;
    }
    

    private void registraDevolucaoDeLivros(Set<Livro> livrosDevolvidos) {

         for (Livro livro : livrosDevolvidos) {
            if(!livrosDisponiveis.containsKey(livro.getIsbn())){
                livro.devolver();
                livrosDisponiveis.put(livro.getIsbn(), livro);
            }
        }
    }


}
