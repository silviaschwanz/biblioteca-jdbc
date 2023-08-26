package com.biblioteca.model.biblioteca;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.biblioteca.exception.BibliotecaInexistenteException;
import com.biblioteca.model.livro.Livro;
import com.biblioteca.model.livro.SituacaoLivro;

public class BibliotecaDAOMysql implements BibliotecaDAO{

    private Connection connection;

    public BibliotecaDAOMysql(Connection connection) {
        this.connection = connection;
    }


    @Override
    public BibliotecaEntity salva(String nomeBiblioteca) {
        String sql = "INSERT INTO biblioteca(nome) VALUES(?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomeBiblioteca);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return buscaPorNome(nomeBiblioteca);            

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(String nomeBiblioteca) {
        String sql = "DELETE FROM biblioteca WHERE nome = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomeBiblioteca);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }       
    }

    @Override
    public Biblioteca buscaPorNome(String nomeBiblioteca) {
        String sql = "SELECT * FROM biblioteca where nome = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomeBiblioteca);
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();

            if(resultSet.next()){
                BibliotecaEntity bibliotecaEntity = new BibliotecaEntity(resultSet.getInt("id"), resultSet.getString("nome"));
                resultSet.close();              
                return bibliotecaEntity;
            }

            throw new BibliotecaInexistenteException();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Livro> buscaLivrosAcervo(String nomeBiblioteca) {

        BibliotecaEntity bibliotecaEntity = buscaPorNome(nomeBiblioteca);
        Map<Long, Livro> livrosDoAcervo = new HashMap<>();

        String sql = "SELECT * FROM livro WHERE biblioteca_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bibliotecaEntity.id());
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                String titulo = resultSet.getString("titulo");
                LocalDate dataPublicacao = resultSet.getDate("data_publicacao").toLocalDate();
                Long isbn = resultSet.getLong("isbn");
                String situacao = resultSet.getString("situacao");
                Livro livro = new Livro(titulo, dataPublicacao, isbn, situacao);
                livrosDoAcervo.put(isbn, livro);
            }

            resultSet.close();
            preparedStatement.close();
            return livrosDoAcervo;

        } catch (SQLException e) {
            throw new RuntimeException();
        }    
    }

    @Override
    public Map<Long, Livro> buscaLivrosDisponiveis(String nomeBiblioteca) {

        BibliotecaEntity bibliotecaEntity = buscaPorNome(nomeBiblioteca);
        Map<Long, Livro> livrosDisponiveis = new HashMap<>();

        String sql = "SELECT * FROM livro WHERE biblioteca_id = ? AND situacao = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bibliotecaEntity.id());
            preparedStatement.setString(2, SituacaoLivro.DISPONIVEL.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String titulo = resultSet.getString("titulo");
                LocalDate dataPublicacao = resultSet.getDate("data_publicacao").toLocalDate();
                Long isbn = resultSet.getLong("isbn");
                String situacao = resultSet.getString("situacao");
                Livro livro = new Livro(titulo, dataPublicacao, isbn, situacao);
                livrosDisponiveis.put(isbn, livro);
            }
            resultSet.close();
            preparedStatement.close();
            return livrosDisponiveis;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
