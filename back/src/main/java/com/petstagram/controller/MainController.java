package com.petstagram.controller;

import com.petstagram.data.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/user")
    public Users user(@RequestParam String userEmail, @RequestParam String userNickname, @RequestParam String userPwd, @RequestParam int userAge, @RequestParam String userImg, @RequestParam boolean isPublic) {
        return new Users(userEmail, userNickname, userPwd, userAge, userImg, isPublic, "");
    }
}
