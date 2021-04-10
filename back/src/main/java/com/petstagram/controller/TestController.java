package com.petstagram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class TestController {

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
}
