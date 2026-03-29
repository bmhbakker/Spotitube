package com.spotitube.mapper;

import com.spotitube.domain.model.User;
import com.spotitube.shared.exception.DatabaseException;
import com.spotitube.shared.exception.GeneralException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public User mapRow(ResultSet rs, String token) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setToken(token);
            return user;
        } catch (SQLException e) {
            throw new DatabaseException("The user Could not be created", e);
        }
    }

    public User mapRow(int id, String username, String token) {
        User user = new User();
        try {
            user.setId(id);
            user.setUsername(username);
            user.setToken(token);
            return user;
        } catch (Exception e){
            throw new GeneralException("Something went wrong", e);
        }
    }
}
