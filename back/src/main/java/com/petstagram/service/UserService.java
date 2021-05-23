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
    public Users getUsers(Users user) {
//        return mongoTemplate.findById("userEmail", Users.class);
        for(Users u : this.getAllUsers()) {
            if(u.getUserEmail().equals(user.getUserEmail()))
                return u;
        }
        return null;
    }

    public Boolean checkSignIn(Users user) {
        return this.getUsers(user).getUserPwd().equals(user.getUserPwd());
    }

    public List<Users> getAllUsers() {
        return mongoTemplate.findAll(Users.class);
    }

    // UPDATE
    public boolean updateUsers(Users user) {
        try {
            Criteria criteria = new Criteria("userNickname");
            criteria.is(user.getUserNickname());

            Query query = new Query(criteria);
            Update update = new Update();
            update.set("userNickname", user.getUserNickname());
// 하나만/
            mongoTemplate.updateFirst(query, update, Users.class);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
