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

}
