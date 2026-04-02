package com.spotitube.init;

import com.spotitube.config.DBConnection;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.sql.Connection;

@Singleton
@Startup
public class DatabaseInitializer {
    private DBConnection dbConnection;

    @Inject
    public DatabaseInitializer(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DatabaseInitializer(){}

    @PostConstruct
    public void init() {
        try (Connection conn = dbConnection.getConnection()) {
            if (conn.getMetaData().getURL().contains("h2")) {
                runSqlScript(conn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runSqlScript(Connection conn) throws Exception {
        InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("db_init_h2.sql");

        assert input != null;
        String sql = new String(input.readAllBytes());

        for (String statement : sql.split(";")) {
            if (!statement.trim().isEmpty()) {
                conn.createStatement().execute(statement);
            }
        }
    }
}