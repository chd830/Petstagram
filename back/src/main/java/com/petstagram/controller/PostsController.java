package com.petstagram.controller;

import com.petstagram.data.Posts;
import com.petstagram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class PostsController {

    @Autowired
    private PostService postService;

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handler(Exception e){
        return handleFail(e.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts/all")
    public ResponseEntity<Map<String, Object>> getPosts(){
        List<Posts> posts = postService.getAll();
        return handleSuccess(posts);
    }

    @PostMapping("/api/v1/posts/insert")
    public ResponseEntity<Map<String, Object>> insertPosts(@RequestBody Posts posts){
        return handleSuccess(postService.insert(posts));
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
}