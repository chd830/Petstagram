package com.petstagram.controller;

import com.petstagram.data.Users;
//import com.petstagram.security.oauth2.user.SessionUser;
import com.petstagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private static UserService userService = new UserService();

    @GetMapping("/api/v1/test")
    public ResponseEntity<String> test(@RequestParam String str) {
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @PostMapping("/api/v1/user")
    public ResponseEntity<Users> getUser(@RequestBody Users user) {
        return new ResponseEntity<>(userService.getUsers(user), HttpStatus.OK);
    }

    @PostMapping( "/api/v1/user/signup")
    public ResponseEntity<Boolean> signup(@RequestBody Users user) {
        System.out.println(user);
        if(userService.getUsers(user) != null)
            return new ResponseEntity<>(false, HttpStatus.OK);
        userService.insert(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/api/v1/user/signin")
    public ResponseEntity<Boolean> signin(@RequestBody Users user) {
        System.out.println("INPUT "+user);
        return new ResponseEntity<>(userService.checkSignIn(user), HttpStatus.OK);
    }
    @PostMapping("/api/v1/user/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody Users user) {
        return new ResponseEntity<>(userService.updateUsers(user), HttpStatus.OK);
    }

}