package com.spotitube.config;

import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://spotitube-db-1/spotitube";
    private static final String USER = "sa";
    private static final String PASS = "Geheim_101";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}