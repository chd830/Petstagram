package com.petstagram.service;

import com.petstagram.data.Users;
import com.petstagram.test.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private MongoTemplate mongoTemplate;

    // INSERT
    public Users insert(Users user) {
        return mongoTemplate.insert(user);
    }

    // SELECT
    public Users getUsers(String userEmail) {
        Users user = mongoTemplate.findById(userEmail, Users.class);
        return Optional.ofNullable(user).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not fount"));
    }

    public List<Users> getAllUsers() {
        return mongoTemplate.findAll(Users.class);
    }

    // UPDATE
    public void updateUsers(Users user) {
//        org.springframework.data.mongodb.core.query.Criteria criteria = new Criteria("userName");
//        criteria.is(name);

//        Query query = new Query(criteria);
//        Update update = new Update();
//        update.set("userName", newname);
// 하나만/
//        mongoTemplate.updateFirst(query, update, Test.class);
    }
}
