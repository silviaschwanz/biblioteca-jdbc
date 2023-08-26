package com.biblioteca;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.biblioteca.exception.CreateConnectionException;

public class ConnectionService {

    Connection connection;

    public Connection openConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3320/biblioteca", 
                "root", 
                "password");
            return connection;
        } catch (SQLException e) {
            throw new CreateConnectionException(e.getMessage());
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
