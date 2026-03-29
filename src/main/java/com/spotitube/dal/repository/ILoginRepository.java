package com.spotitube.dal.repository;


import com.spotitube.api.dto.helper.AuthCandidate;
import com.spotitube.domain.model.User;

public interface ILoginRepository {
    User getUserByToken(String token);
    AuthCandidate authenticate(String username, String token);
}
