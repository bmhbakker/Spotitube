package com.spotitube.dal.repository;


import com.spotitube.domain.model.User;

public interface ILoginRepository {
    User findUserByUsername(String username);

    User saveToken(int id, String token);

    User findUserByToken(String token);
}