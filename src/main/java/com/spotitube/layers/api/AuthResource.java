package com.spotitube.layers.api;

import com.spotitube.DBConnection;
import com.spotitube.layers.domain.Response.LoginResponse;
import com.spotitube.layers.domain.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Path("/login")
public class AuthResource {
    private DBConnection dbConnection;
    private LoginResponse loginResponse;
    private User user;

    @POST
    public Response login(LoginRequest login) throws SQLException {
        Connection conn = dbConnection.getConnection();

        String sql = "SELECT password FROM owner WHERE username = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, login.user);
        ResultSet rs = pStmt.executeQuery();

        if (!rs.next() || !rs.getString("password").equals(login.password)) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid username or password")
                    .build();
        }

        String token = UUID.randomUUID().toString();
        LoginResponse LR = new LoginResponse(login.user, token);
        user.setUsername(login.user);
        user.setToken(token);

        loginResponse.setUsername(login.user);
        loginResponse.setToken(token);

        return Response
                .status(Response.Status.OK)
                .entity(LR)
                .build();
    }

    @Inject
    public void setDB(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Inject
    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    @Inject
    public void setUser(User user) {
        this.user = user;
    }
}
