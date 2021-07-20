package com.petstagram.controller;

import com.petstagram.data.Users;
import com.petstagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static UserService userService = new UserService();

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handler(Exception e){
        return handleFail(e.getMessage(), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> handleSuccess(Object data){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("state", "ok");
        resultMap.put("data", data);
        return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("state", "fail");
        resultMap.put("data", data);
        return new ResponseEntity<Map<String,Object>>(resultMap, status);
    }

    @GetMapping("/api/v1/test")
    public ResponseEntity<Map<String, Object>> test(@RequestParam String str) {
        return handleSuccess(str);
    }

    @PostMapping("/api/v1/user")
    public ResponseEntity<Map<String, Object>> getUser(@RequestBody Users user) {
        System.out.println(userService.getUsers(user));
        return handleSuccess(userService.getUsers(user));
    }
      
    @PostMapping("/api/v1/user/update")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody Users user) {
        return handleSuccess(userService.updateUsers(user));
    }

    @PostMapping("/api/v1/sendmail")
    public ResponseEntity<Map<String, Object>> sendMail(@RequestBody Users user) {
        System.out.println(user);
        return handleSuccess(userService.sendMail(user));
    }
}