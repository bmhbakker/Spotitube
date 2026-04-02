package com.spotitube.dal.repository.impl;

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
    private DBConnection dbConnection;
    private UserMapper userMapper;

    public LoginRepository() {
    }  //ONLY NEEDED FOR PROXYING

    @Inject
    public LoginRepository(DBConnection dbConnection, UserMapper userMapper) {
        this.dbConnection = dbConnection;
        this.userMapper = userMapper;
    }

    @Override
    public User findUserByUsername(String username) {
        String SelectSQL = "SELECT id, username, password FROM users WHERE username = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(SelectSQL)) {

            pStmt.setString(1, username);

            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    return userMapper.mapWithPassword(rs);
                } else {
                    throw new NotFoundException("User not found with username: " + username);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch user by username", e);
        }
    }

    @Override
    public User saveToken(int id, String token) {
        String updateSQL = "UPDATE users SET token = ? WHERE id = ?";
        String selectSQL = "SELECT id, username FROM users WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(updateSQL)) {
            pStmt.setString(1, token);
            pStmt.setInt(2, id);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to save token", e);
        }

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(selectSQL)) {
            pStmt.setInt(1, id);

            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    return userMapper.mapRow(rs, token);
                } else {
                    throw new NotFoundException("User not found with id: " + id);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch user after saving token", e);
        }
    }

    @Override
    public User findUserByToken(String token) {
        String SelectSQL = "SELECT id, username FROM users where token = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(SelectSQL)) {
            pStmt.setString(1, token);

            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    return userMapper.mapRow(rs, token);
                } else {
                    throw new NotAuthorizedException("Invalid token");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch user by token", e);
        }
    }
}
