package com.petstagram.controller;

import com.petstagram.data.Users;
import com.petstagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class UserController {

    @Autowired
    UserService userService;

//    @GetMapping("/")
//    public String main() {
//        return "index";
//    }

    @PostMapping( "/api/v1/user/signup")
    public void signup(@RequestBody Users user) {
        System.out.println(user);
        userService.insert(user);
    }

    @PostMapping("/api/v1/user/signin")
    public ResponseEntity<Boolean> signin(@RequestBody Users user) {
        userService.getUsers(user);
        return new ResponseEntity<>(userService.checkSignIn(user), HttpStatus.OK);
    }
    @PostMapping("/api/v1/user/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody Users user) {
        return new ResponseEntity<>(userService.updateUsers(user), HttpStatus.OK);
    }

}