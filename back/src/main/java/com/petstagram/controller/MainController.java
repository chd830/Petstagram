package com.petstagram.controller;

import com.petstagram.data.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class MainController {

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/user")
    public Users getUser(@RequestParam String userEmail, @RequestParam String userPwd) {
        System.out.println(userEmail+"\t"+userPwd);
        Users user = new Users();
        user.setUserEmail(userEmail);
        user.setUserPwd(userPwd);
        System.out.println(user);
        return user;
    }

    @PostMapping(path = "/user")
    public void user(@RequestBody Users user) {
        System.out.println(user);
    }

}