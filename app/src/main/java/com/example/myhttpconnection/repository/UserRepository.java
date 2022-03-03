package com.example.myhttpconnection.repository;

import com.example.myhttpconnection.models.User;

public interface UserRepository {

    // C R U D
    void create(String id, String pw);
    User read(String id);
    void update(User user);
    void delete(String id);

}
