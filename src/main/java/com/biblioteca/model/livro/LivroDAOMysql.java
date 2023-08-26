package com.biblioteca.model.livro;

import java.sql.*;

import com.biblioteca.exception.LivroInexistenteException;

public class LivroDAOMysql implements LivroDAO{


    private Connection connection;

    public LivroDAOMysql(Connection connection){
        this.connection = connection;
    }


    @Override
    public LivroEntity salva(DadosLivro dadosLivro) {

        String sql = "INSERT INTO livro(biblioteca_id, titulo, data_publicacao, isbn, situacao) VALUES (?,?,?,?,?)";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dadosLivro.bibliotecaId());
            preparedStatement.setString(2, dadosLivro.titulo());
            preparedStatement.setDate(3, Date.valueOf(dadosLivro.dataPublicacao()));
            preparedStatement.setLong(4, dadosLivro.isbn());
            preparedStatement.setString(5, dadosLivro.situacao());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            return buscaPorIsbn(dadosLivro.isbn());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Long isbn) {
        
        String sql = "DELETE FROM livro WHERE isbn = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, isbn);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public LivroEntity altera(LivroEntity livroEntity) {
        String sql = "UPDATE livro SET biblioteca_id = ?, titulo = ?, data_publicacao = ?, isbn = ?, situacao = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, livroEntity.bibliotecaId());
            preparedStatement.setString(2, livroEntity.titulo());
            preparedStatement.setDate(3, Date.valueOf(livroEntity.dataPublicacao()));
            preparedStatement.setLong(4, livroEntity.isbn());
            preparedStatement.setString(5, livroEntity.situacao());
            preparedStatement.setInt(6, livroEntity.id());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            return buscaPorIsbn(livroEntity.isbn());

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public LivroEntity buscaPorIsbn(Long isbn) {

        String sql = "SELECT * from livro WHERE isbn = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();

            if(resultSet.next()){
                LivroEntity livroEntity = new LivroEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("biblioteca_id"),
                    resultSet.getString("titulo"), 
                    resultSet.getDate("data_publicacao").toLocalDate(), 
                    resultSet.getLong("isbn"), 
                    resultSet.getString("situacao")
                );
                resultSet.close();
                return livroEntity;
            }

            throw new LivroInexistenteException();

        } catch (SQLException e) {
           throw new RuntimeException();
        }

    }


    @Override
    public LivroEntity buscaPorTitulo(String tituloBusca) {
       String sql = "SELECT * FROM livro WHERE titulo = ?";

       try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tituloBusca);

            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();

            if(resultSet.next()){
                LivroEntity livroEntity = new LivroEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("biblioteca_id"),
                    resultSet.getString("titulo"), 
                    resultSet.getDate("data_publicacao").toLocalDate(), 
                    resultSet.getLong("isbn"),
                    resultSet.getString("situacao") 
                );
                resultSet.close();
                return livroEntity;
            }
            throw new LivroInexistenteException();       

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    
}
