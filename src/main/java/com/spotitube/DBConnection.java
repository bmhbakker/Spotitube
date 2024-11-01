package com.spotitube;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DBConnection {
    private Connection conn;

    @PostConstruct
    public void setupDatabase() {
        try {
            String DB_URL = "jdbc:mysql://spotitube-db-1/spotitube";
            String USER = "sa";
            String PASS = "Geheim_101";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
