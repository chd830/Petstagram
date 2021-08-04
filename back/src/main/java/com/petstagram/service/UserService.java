package com.petstagram.service;

import com.petstagram.data.Users;

import java.util.List;

public interface UserService {
    Users insert(Users ser);
    Users getUsers(Users user);
    List<Users> getAllUsers();
    boolean checkSignIn(Users user);
    boolean updateUsers(Users user);
    boolean sendMail(Users user);
}
