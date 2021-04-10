package com.petstagram.service;

import com.petstagram.test.RestException;
import com.petstagram.test.Test;
import com.petstagram.test.TestRepository;
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
public class TestService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TestRepository testRepository;

    public Test getTest(String id){
        Test test = mongoTemplate.findById(id, Test.class);
        return Optional.ofNullable(test).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not fount"));
    }

    public List<Test> getAll(){
        return mongoTemplate.findAll(Test.class);
    }

    public List<Test> getTestList(String userName){
        try{
            Query query = new Query().addCriteria(Criteria.where("userName").is(userName));
            return mongoTemplate.find(query, Test.class);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Test insert(Test test){
        return mongoTemplate.insert(test);
    }

    public Test getTest2(String id){
        return testRepository.findById(id).orElseThrow(()-> new RestException(HttpStatus.NOT_FOUND, "Not fount"));
    }

    public List<Test> getTestList2(String userName){
        return testRepository.findByuserName(userName);
    }

    public Test insert2(Test test){
        return testRepository.insert(test);
    }

    public void removeByname(String value){
        Criteria criteria = new Criteria("userName");
        criteria.is(value);

        Query query = new Query(criteria);
        mongoTemplate.remove(query, Test.class);
    }

    public void removeall(){
        mongoTemplate.remove(new Query(), Test.class);
    }

    public void update(String name, String newname){
        Criteria criteria = new Criteria("userName");
        criteria.is(name);

        Query query = new Query(criteria);
        Update update = new Update();
        update.set("userName", newname);
// 하나만
//        mongoTemplate.updateFirst(query, update, Test.class);
// 모든것 수정
        mongoTemplate.updateMulti(query, update, Test.class);

//        조건없이
//        mongoTemplate.updateMulti(new Query(), update, Test.class);
    }

}
