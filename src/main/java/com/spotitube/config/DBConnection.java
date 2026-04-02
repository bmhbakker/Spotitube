package com.spotitube.config;

import com.spotitube.shared.exception.DatabaseSetupException;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@ApplicationScoped
public class DBConnection {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public DBConnection() {
        Properties props = new Properties();
        try (var inStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inStream == null) {
                throw new RuntimeException("config.properties not found!");
            }
            props.load(inStream);
        } catch (Exception e) {
            throw new RuntimeException("Can't load config.properties " + e.getMessage(), e);
        }

        String dbType = props.getProperty("db.type").trim();
        DB_URL = props.getProperty(dbType + ".url").trim();
        DB_USER = props.getProperty(dbType + ".user").trim();
        DB_PASSWORD = props.getProperty(dbType + ".password").trim();
    }

    public Connection getConnection() {
        int attempts = 0;
        int MAX_RETRIES = 10;
        while (attempts < MAX_RETRIES) {
            try {
                return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                attempts++;
                System.out.println("RETRYING DATABASE CONNECTION, ATTEMPT: " + attempts);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new DatabaseSetupException("Can't connect to database. InterruptedException" + ex.getMessage());
                }
            }
        }
        throw new DatabaseSetupException("Can't connect to database after " + MAX_RETRIES + " attempts");
    }
}