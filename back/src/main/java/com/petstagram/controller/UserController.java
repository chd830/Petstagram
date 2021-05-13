package com.petstagram.controller;

import com.petstagram.data.Users;
import com.petstagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

//    @GetMapping("/user")
//    public Users getUser(@RequestParam String userEmail, @RequestParam String userPwd) {
//        System.out.println(userEmail+"\t"+userPwd);
//        Users user = new Users();
//        user.setUserEmail(userEmail);
//        user.setUserPwd(userPwd);
//        System.out.println(user);
//        return userService.insert(user);
//    }

    @PostMapping(path = "/user")
    public void user(@RequestBody Users user) {
        System.out.println(user);
        userService.insert(user);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<Boolean> updateUser(@RequestBody Users user) {
        System.out.println(user);
        return new ResponseEntity<>(userService.updateUsers(user), HttpStatus.OK);
    }

}