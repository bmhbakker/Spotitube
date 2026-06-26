package com.spotitube.api.resource;
import com.spotitube.api.dto.helper.AuthCandidate;
import com.spotitube.api.dto.request.LoginRequest;
import com.spotitube.api.dto.response.LoginResponse;
import com.spotitube.service.LoginService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthResourceTest {
    LoginService loginServiceMock;
    AuthResource sut;

    @BeforeEach
    void Init(){
        loginServiceMock = mock(LoginService.class);
        sut = new AuthResource(loginServiceMock);
    }

    @Test
    void AuthResource_login_returnsOkResponse() {
        // Arrange
        LoginService loginServiceMock = mock(LoginService.class);
        AuthResource sut = new AuthResource(loginServiceMock);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.user = "username";
        loginRequest.password = "password";

        AuthCandidate candidate = new AuthCandidate(1, "user", "token");
        when(loginServiceMock.authenticate("username", "password")).thenReturn(candidate);

        // Act
        Response actualResponse = sut.login(loginRequest);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());

        LoginResponse entity = (LoginResponse) actualResponse.getEntity();
        assertEquals("user", entity.getUser());
        assertEquals("token", entity.getToken());

        verify(loginServiceMock).authenticate("username", "password");
    }

    @Test
    void AuthResource_login_throwsBadRequest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.user = null;
        loginRequest.password = "password";

        //Act & Assert
        assertThrows(BadRequestException.class, () -> sut.login(loginRequest));
        verifyNoInteractions(loginServiceMock);
    }
}