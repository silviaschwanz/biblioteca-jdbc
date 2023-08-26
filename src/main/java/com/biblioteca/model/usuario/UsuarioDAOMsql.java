package com.biblioteca.model.usuario;

import java.sql.*;

import com.biblioteca.exception.UsuarioInexistenteException;


public class UsuarioDAOMsql implements UsarioDAO{

    private Connection connection;

    
    public UsuarioDAOMsql(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Usuario salva(DadosUsuario dadosUsuario) {

        String sql = "INSERT INTO usuario(biblioteca_id, nome, cpf) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, dadosUsuario.biblioteca_id());
            preparedStatement.setString(2, dadosUsuario.nome());
            preparedStatement.setString(3, dadosUsuario.cpf());
            
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return buscaPorCPF(dadosUsuario.cpf());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void remove(String cpf) {
        
        String sql = "DELETE FROM usuario WHERE cpf = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cpf);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
           throw new RuntimeException(e);
        }   

    }

    @Override
    public void altera(DadosUsuario cadastroUsuario) {
        
        String sql = "UPDATE usuario SET nome = ?, biblioteca_id = ? WHERE cpf = ? ";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, cadastroUsuario.nome());
            preparedStatement.setInt(2, cadastroUsuario.biblioteca_id());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
             throw new RuntimeException(e);
        }       

    }


    @Override
    public Usuario buscaPorCPF(String cpf) {
        String sql = "SELECT * FROM usuario WHERE cpf = ?  ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, cpf);

            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();

            if(resultSet.next()) {

                Usuario usuario = new Usuario(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("cpf"));
                resultSet.close();                
                return usuario;
            }
            throw new UsuarioInexistenteException();
         
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }       

    }



    
}
