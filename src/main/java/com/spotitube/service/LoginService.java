package com.spotitube.service;

import com.spotitube.api.dto.helper.AuthCandidate;
import com.spotitube.dal.repository.ILoginRepository;
import com.spotitube.dal.repository.impl.LoginRepository;
import com.spotitube.domain.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;

import java.util.UUID;

public class LoginService {
    private ILoginRepository loginRepository;

    public LoginService() { } //ONLY NEEDED FOR PROXYING

    @Inject
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public AuthCandidate authenticate(String username, String password) {
        User user = loginRepository.findUserByUsername(username);

        // Validate credentials
        if (user == null || !user.getPassword().equals(password)) {
            throw new NotAuthorizedException("Invalid username or password");
        }

        String token = UUID.randomUUID().toString();
        User updatedUser = loginRepository.saveToken(user.getId(), token);

        if (updatedUser == null) {
            throw new NotFoundException("User not found after updating token");
        }

        return new AuthCandidate(updatedUser.getId(), updatedUser.getUsername(), token);
    }

    public User validateAuth(String token) {
        User user = loginRepository.findUserByToken(token);
        if (user == null) {
            throw new NotAuthorizedException("Invalid token");
        }
        return user;
    }
}
