package com.spotitube.dal.repository.impl;

import com.spotitube.api.dto.helper.AuthCandidate;
import com.spotitube.config.DBConnection;
import com.spotitube.dal.repository.ILoginRepository;
import com.spotitube.domain.model.User;
import com.spotitube.mapper.UserMapper;
import com.spotitube.shared.exception.DatabaseException;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class LoginRepository implements ILoginRepository {
    private final DBConnection dbConnection;
    private final UserMapper userMapper;

    @Inject
    public LoginRepository(DBConnection dbConnection, UserMapper userMapper) {
        this.dbConnection = dbConnection;
        this.userMapper = userMapper;
    }


    public AuthCandidate authenticate(String username, String password) {
        String SelectSQL = "SELECT id, username, password FROM users WHERE username = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(SelectSQL)) {
            pStmt.setString(1, username);

            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");

                    if (storedPassword.equals(password)) {
                        return new AuthCandidate(rs.getInt("id"), rs.getString("username"));
                    }
                }
            }
            return null; //invalid username or password

        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch password from user", e);
        }
    }

    public User saveTokenForUser(int id, String token) {
        String updateSQL = "UPDATE users SET token = ? WHERE id = ?";
        String selectSQL = "SELECT username FROM users WHERE id = ?";

        try (Connection conn = dbConnection.getConnection()) {
            try (PreparedStatement pStmt = conn.prepareStatement(updateSQL)) {
                pStmt.setString(1, token);
                pStmt.setInt(2, id);
                pStmt.executeUpdate();
            }

            try (PreparedStatement pStmt = conn.prepareStatement(selectSQL)) {
                pStmt.setInt(1, id);
                ResultSet rs = pStmt.executeQuery();
                if (rs.next()) {
                    return userMapper.mapRow(id, rs.getString("username"), token);
                } else {
                    throw new NotFoundException("User not found");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not save token or retrieve user", e);
        }
    }


    @Override
    public User getUserByToken(String token) {
        String SelectSQL = "SELECT id, username FROM users where token = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(SelectSQL)) {
            pStmt.setString(1, token);
            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    return userMapper.mapRow(rs, token);
                }
                throw new NotAuthorizedException("Invalid token");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch user by token", e);
        }
    }
}

