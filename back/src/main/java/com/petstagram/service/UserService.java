package com.petstagram.service;

import com.mongodb.*;
import com.petstagram.data.Users;
import com.petstagram.test.RestException;
import com.petstagram.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://127.0.0.1:27017/test"));

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
        System.out.println(user);
        return this.getUsers(user).getUserPwd().equals(user.getUserPwd());
    }

    public List<Users> getAllUsers() {
        return mongoTemplate.findAll(Users.class);
    }

    // UPDATE
    public boolean updateUsers(Users user) {
        System.out.println(user);
        try {
            Users prev = getUsers(user);

            // 기준이 되는 정보
            Criteria criteria = new Criteria("userEmail");
            criteria.is(prev.getUserEmail());

            // 수정할 정보
            Query query = new Query(criteria);
            Update update = new Update();
            update.set("userNickname", user.getUserNickname());
            update.set("userImg", user.getUserImg());

            // 하나만 실행
//            mongoTemplate.updateFirst(query, update, Users.class);
//            수정할 게 여러개일 때
            mongoTemplate.updateMulti(query, update, Users.class);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}