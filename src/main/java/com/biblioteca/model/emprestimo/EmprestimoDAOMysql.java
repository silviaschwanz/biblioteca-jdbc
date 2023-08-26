package com.biblioteca.model.emprestimo;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.biblioteca.exception.EmprestimoInexistenteException;
import com.biblioteca.model.livro.Livro;
import com.biblioteca.model.usuario.Usuario;

public class EmprestimoDAOMysql implements EmprestimoDAO{

    private Connection connection;

    
    public EmprestimoDAOMysql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public EmprestimoEntity salva(Emprestimo emprestimo) {

        String sql = "INSERT INTO emprestimo(usuario_id, data_retirada, data_devolucao, finalizado) VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, emprestimo.getUsuario().getId());
            preparedStatement.setDate(2, Date.valueOf(emprestimo.getDataRetirada()));
            preparedStatement.setDate(3, Date.valueOf(emprestimo.getDataDevolucao()));
            preparedStatement.setBoolean(4, emprestimo.getFinalizado());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            EmprestimoEntity emprestimoEntity = buscaEmprestimoPorDataAndUsuario(emprestimo.getDataRetirada(), emprestimo.getUsuario().getId());
            salvaLivrosEmprestados(emprestimoEntity);

            return buscaEmprestimoPorDataAndUsuario(emprestimo.getDataRetirada(), emprestimo.getUsuario().getId());

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private void salvaLivrosEmprestados(EmprestimoEntity emprestimoEntity) {

        PreparedStatement preparedStatement;

        for (Integer livro_id : emprestimoEntity.livrosEmprestados()) {
            String sql = "INSERT INTO emprestimo_livro(emprestimo_id, livro_id) VALUES (?,?)";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, emprestimoEntity.id());
                preparedStatement.setInt(2, livro_id);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }

    }
 

    @Override
    public EmprestimoEntity finalizaEmprestimo(EmprestimoEntity emprestimoEntity) {
        
        String sql = "UPDATE emprestimo SET finalizado = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, emprestimoEntity.id());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return emprestimoEntity;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        
    }

    @Override
    public EmprestimoEntity buscaEmprestimoPorDataAndUsuario(LocalDate dataRetirada, Integer usuarioId) {

        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ? AND data_retirada = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, usuarioId);
            preparedStatement.setDate(2, Date.valueOf(dataRetirada));

            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();

            Set<Integer> livros = buscaLivrosDoEmprestimo(dataRetirada, usuarioId);

            if(resultSet.next()){
                EmprestimoEntity emprestimoEntity = new EmprestimoEntity(
                    resultSet.getInt("id"), 
                    resultSet.getInt("usuario_id"), 
                    resultSet.getDate("data_retirada").toLocalDate(), 
                    resultSet.getDate("data_devolucao").toLocalDate(),
                    resultSet.getBoolean("finalizado"),
                    livros
                );
                resultSet.close();
                return emprestimoEntity;
            }
            throw new EmprestimoInexistenteException();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Set<Integer> buscaIdsLivros(LocalDate dataRetirada, Integer usuarioId) {

        Set<Integer> livrosIds = new HashSet<>();
        EmprestimoEntity emprestimo = buscaEmprestimoPorDataAndUsuario(dataRetirada, usuarioId);
        Integer emprestimoId = emprestimo.id();

        String sql = "SELECT * FROM emprestimo_livro WHERE emprestimo_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, emprestimoId);

            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();

            while(resultSet.next()){
                livrosIds.add(resultSet.getInt("livro_id"));
            }
            resultSet.close();

            return livrosIds;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Set<Livro> buscaLivrosDoEmprestimo(LocalDate dataRetirada, Integer usuarioId) {

        Set<Livro> livrosDoEmprestimo = new HashSet<>();
        EmprestimoEntity emprestimo = buscaEmprestimoPorDataAndUsuario(dataRetirada, usuarioId);
        Integer emprestimoId = emprestimo.id();

        

        
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscaLivrosDoEmprestimo'");
    }
    
}
