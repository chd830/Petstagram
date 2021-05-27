package com.petstagram.controller;

import com.petstagram.data.Comments;
import com.petstagram.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentsController {

    @Autowired
    private CommentsService commentService;

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handler(Exception e) {
        return handleFail(e.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/comments/all")
    public ResponseEntity<Map<String, Object>> getComments() {
        List<Comments> comments = commentService.getAll();
        return handleSuccess(comments);
    }

    @GetMapping("/api/v1/comments/commentNo")
    public ResponseEntity<Map<String, Object>> getBycommentNo(@RequestParam int commentNo) {
        Comments comments = commentService.getBycommentNo(commentNo);
        return handleSuccess(comments);
    }

    @GetMapping("/api/v1/comments/userEmail")
    public ResponseEntity<Map<String, Object>> getByUsers(@RequestParam String userEmail) {
        List<Comments> comments = commentService.getByuserEmail(userEmail);
        return handleSuccess(comments);
    }

    @PostMapping("/api/v1/comments/insert")
    public ResponseEntity<Map<String, Object>> insertComment(@RequestBody Comments comments) {
        int commentNo = commentService.getAll().size() + 1;
        comments.setCommentNo(commentNo);
        return handleSuccess(commentService.insert(comments));
    }

    @PostMapping("/api/v1/comments/update")
    public ResponseEntity<Map<String, Object>> updateComment(@RequestBody Comments comments) {
        return handleSuccess(commentService.update(comments));
    }

    public ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("state", "ok");
        resultMap.put("data", data);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("state", "fail");
        resultMap.put("data", data);
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
