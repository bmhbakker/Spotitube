package com.spotitube.mapper;

import com.spotitube.domain.model.User;
import com.spotitube.shared.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public User mapWithPassword(ResultSet rs) {
        try {
            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    null // token not needed here
            );
        } catch (SQLException e) {
            throw new DatabaseException("The user Could not be created", e);
        }
    }

    public User mapRow(ResultSet rs, String token) {
        try{
            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    null, // password not needed
                    token
            );
        } catch (SQLException e) {
            throw new DatabaseException("The user Could not be created", e);
        }
    }
}