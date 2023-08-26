package com.biblioteca.model.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import com.biblioteca.exception.ListaDeLivrosVaziaException;
import com.biblioteca.exception.UsuarioInexistenteException;
import com.biblioteca.exception.UsuarioJaExisteExcpetion;
import com.biblioteca.model.emprestimo.Emprestimo;
import com.biblioteca.model.livro.Livro;
import com.biblioteca.model.usuario.Usuario;

public class BibliotecaTest {
    
    
    @Test
    public void givenUsuario_whenCadastraUsuario_thenReturnUsuario(){
        Usuario usuario = new Usuario("011.254.156-45", "Joao das Couves");
        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");

        Usuario returnUsusario = biblioteca.cadastraUsuario(usuario);
        assertEquals(usuario.getCpf(), returnUsusario.getCpf());
    }

    @Test
    public void givenUsuario_whenCadastraUsuario_thenReturnUsuarioJaExisteException(){
        Usuario usuario = new Usuario("011.254.156-45", "Joao das Couves");
        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");
        biblioteca.cadastraUsuario(usuario);

        assertThrows(UsuarioJaExisteExcpetion.class, () -> biblioteca.cadastraUsuario(usuario));
    }

    @Test
    public void givenLivro_whenCadastraLivro_thenReturnLivro() {
        Livro livro = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 1258796321254L);
        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");

        Livro returnLivro = biblioteca.cadastraLivro(livro);
        assertEquals(livro.getIsbn(), returnLivro.getIsbn());
    }

    @Test
    public void givenLivro_whenRemoveLivro_thenReturnLivrosAcervo() {
        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");
        
        Livro livro = new Livro("A Casa de Plastico", LocalDate.of(2018, 12, 21), 1258796321254L);
        biblioteca.cadastraLivro(livro);
        
        Livro livro2 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 2258796321254L);
        biblioteca.cadastraLivro(livro2);

        Livro livro3 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 3258796321254L);
        biblioteca.cadastraLivro(livro3);


        Livro livroParaRemover = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 1258796321254L);
        
        Map<Long, Livro> returnLivrosAcervo = biblioteca.removeLivro(livroParaRemover);
        assertEquals(2, returnLivrosAcervo.size());
    }

    @Test
    public void givenTitulo_whenBuscaLivrosDisponiveisPeloTitulo_thenReturnLivros() {
        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");
        
        Livro livro = new Livro("A Casa de Plastico", LocalDate.of(2018, 12, 21), 1258796321254L);
        biblioteca.cadastraLivro(livro);
        
        Livro livro2 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 2258796321254L);
        biblioteca.cadastraLivro(livro2);

        Livro livro3 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 3258796321254L);
        biblioteca.cadastraLivro(livro3);

        List<Livro> returnLivros = biblioteca.buscaLivrosDisponiveisPeloTitulo("A Casa de Papel");
        assertEquals(2, returnLivros.size());
    }

    @Test
    public void givenUsuarioAndTitulos_whenEmprestaLivros_thenReturnEmprestimo() {
        Usuario usuario = new Usuario("011.254.156-45", "Joao das Couves");
        List<String> livrosSolicitados = new ArrayList<>();
        livrosSolicitados.add("A Casa de Papel");
        livrosSolicitados.add("A Casa de Plastico");

        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");
        biblioteca.cadastraUsuario(usuario);
        Livro livro = new Livro("A Casa de Plastico", LocalDate.of(2018, 12, 21), 1258796321254L);
        biblioteca.cadastraLivro(livro);        
        Livro livro2 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 2258796321254L);
        biblioteca.cadastraLivro(livro2);
        Livro livro3 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 3258796321254L);
        biblioteca.cadastraLivro(livro3);

        Emprestimo returnEmprestimo = biblioteca.emprestaLivros(usuario, livrosSolicitados);
        assertEquals(2, returnEmprestimo.getLivros().size());
        assertEquals(usuario.getCpf(), returnEmprestimo.getUsuario().getCpf());
        assertEquals(1, biblioteca.getLivrosDisponiveis().size());
    }

    @Test
    public void givenUsuarioAndTitulos_whenEmprestaLivros_thenReturnUsuarioInexistenteException() {
        Usuario usuario = new Usuario("011.254.156-45", "Joao das Couves");
        List<String> livrosSolicitados = new ArrayList<>();
        livrosSolicitados.add("A Casa de Papel");
        livrosSolicitados.add("A Casa de Plastico");

        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");
        Livro livro = new Livro("A Casa de Plastico", LocalDate.of(2018, 12, 21), 1258796321254L);
        biblioteca.cadastraLivro(livro);        
        Livro livro2 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 2258796321254L);
        biblioteca.cadastraLivro(livro2);
        Livro livro3 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 3258796321254L);
        biblioteca.cadastraLivro(livro3);

        assertThrows(UsuarioInexistenteException.class, () -> biblioteca.emprestaLivros(usuario, livrosSolicitados));
    }
    
    @Test
    public void givenUsuarioAndTitulos_whenEmprestaLivros_thenReturnListaDeLivrosVaziaException() {
        Usuario usuario = new Usuario("011.254.156-45", "Joao das Couves");
        List<String> livrosSolicitados = new ArrayList<>();

        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");
        biblioteca.cadastraUsuario(usuario);

        Livro livro = new Livro("A Casa de Plastico", LocalDate.of(2018, 12, 21), 1258796321254L);
        biblioteca.cadastraLivro(livro);        
        Livro livro2 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 2258796321254L);
        biblioteca.cadastraLivro(livro2);
        Livro livro3 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 3258796321254L);
        biblioteca.cadastraLivro(livro3);

        assertThrows(ListaDeLivrosVaziaException.class, () -> biblioteca.emprestaLivros(usuario, livrosSolicitados));
    }

    @Test
    public void givenEmprestimo_whenFinalizaEmprestimo_thenReturnUsuario() {
        Usuario usuario = new Usuario("011.254.156-45", "Joao das Couves");
        List<String> livrosSolicitados = new ArrayList<>();
        livrosSolicitados.add("A Casa de Papel");
        livrosSolicitados.add("A Casa de Plastico");

        Biblioteca biblioteca = new Biblioteca("Biblioteca Municipal");
        biblioteca.cadastraUsuario(usuario);
        Livro livro = new Livro("A Casa de Plastico", LocalDate.of(2018, 12, 21), 1258796321254L);
        biblioteca.cadastraLivro(livro);        
        Livro livro2 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 2258796321254L);
        biblioteca.cadastraLivro(livro2);
        Livro livro3 = new Livro("A Casa de Papel", LocalDate.of(2018, 12, 21), 3258796321254L);
        biblioteca.cadastraLivro(livro3);

        Emprestimo emprestimo = biblioteca.emprestaLivros(usuario, livrosSolicitados);
        Usuario returnUsuario = biblioteca.finalizaEmprestimo(emprestimo);
        assertEquals(0, returnUsuario.getEmprestimos().size());
    }



}
