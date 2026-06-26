package com.spotitube.service;

import com.spotitube.api.dto.helper.AuthCandidate;
import com.spotitube.dal.repository.ILoginRepository;
import com.spotitube.domain.model.User;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {
    private ILoginRepository loginRepositoryMock;
    private String username;
    private String password;
    private int id;
    private User user;
    private User updatedUser;


    @BeforeEach
    void init() {
        loginRepositoryMock = mock(ILoginRepository.class);

        username = "username";
        password = "password";
        id = 1;

        user = new User(id, username, password, null);
        updatedUser = new User(id, username, password, "generatedToken");
    }


    @Test
    void authenticate_validCredentials_returnsAuthCandidate() {
        // Arrange
        when(loginRepositoryMock.findUserByUsername(username)).thenReturn(user);
        when(loginRepositoryMock.saveToken(eq(id), anyString())).thenReturn(updatedUser);

        LoginService sut = new LoginService(loginRepositoryMock);

        // Act
        AuthCandidate auctualResult = sut.authenticate(username, password);

        // Assert
        assertNotNull(auctualResult);
        assertEquals(id, auctualResult.getId());
        assertEquals(username, auctualResult.getUsername());
        assertNotNull(auctualResult.getToken());

        verify(loginRepositoryMock).findUserByUsername(username);
        verify(loginRepositoryMock).saveToken(eq(id), anyString());
    }

    @Test
    void authenticate_noUser_throwsNotAuthorizedException() {
        // Arrange
        when(loginRepositoryMock.findUserByUsername(username)).thenReturn(null);

        LoginService sut = new LoginService(loginRepositoryMock);

        // Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.authenticate(username, password));

        verify(loginRepositoryMock).findUserByUsername(username);
        verifyNoMoreInteractions(loginRepositoryMock);
    }

    @Test
    void authenticate_noUpdatedUser_throwsNotAuthorizedException() {
        // Arrange
        when(loginRepositoryMock.findUserByUsername(username)).thenReturn(user);
        when(loginRepositoryMock.saveToken(eq(id), anyString())).thenReturn(null);

        LoginService sut = new LoginService(loginRepositoryMock);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sut.authenticate(username, password));

        verify(loginRepositoryMock).findUserByUsername(username);
        verify(loginRepositoryMock).saveToken(eq(id), anyString());
        verifyNoMoreInteractions(loginRepositoryMock);
    }

    @Test
    void validateAuth_validToken_returnsUser(){
        // Arrange
        String token = "validToken";
        when(loginRepositoryMock.findUserByToken(token)).thenReturn(user);

        LoginService sut = new LoginService(loginRepositoryMock);

        // Act
        User actualUser = sut.validateAuth(token);

        // Assert
        assertNotNull(actualUser);
        assertEquals(actualUser, user);
        verify(loginRepositoryMock).findUserByToken(token);
    }

    @Test
    void validateAuth_invalidToken_throwsNotAuthorizedException(){
        // Arrange
        String token = "invalidToken";
        LoginService sut = new LoginService(loginRepositoryMock);
        when(loginRepositoryMock.findUserByToken(token)).thenReturn(null);

        // Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.validateAuth(token));
        verify(loginRepositoryMock).findUserByToken(token);
    }
}