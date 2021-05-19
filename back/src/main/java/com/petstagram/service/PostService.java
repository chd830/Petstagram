package com.petstagram.service;

import com.petstagram.data.Posts;
import com.petstagram.test.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService{

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Posts> getAll(){
        try {
            List<Posts> posts = mongoTemplate.findAll(Posts.class);
            return Optional.ofNullable(posts).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not found"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Posts getBypostNo(int postsNo){
        try{
            Posts post = mongoTemplate.findById(postsNo, Posts.class);
            return Optional.ofNullable(post).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not found"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Posts> getByuserEmail(String userEmail){
        try{
            List<Posts> post = mongoTemplate.find(new Query().addCriteria(Criteria.where("userEmail").is(userEmail)), Posts.class);
            return post;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Posts posts){
        try{
            mongoTemplate.insert(posts);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(Posts posts){
        try{

            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteBypostNo(int postNo){
        try{
            Criteria criteria = new Criteria("postNo");
            criteria.is(postNo);
            Query query = new Query(criteria);
            mongoTemplate.remove(query, Posts.class);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // SELECT
    public Posts getPosts(String userEmail) {
        Criteria criteria = new Criteria("userEmail");
        criteria.is(userEmail);

        Query query = new Query(criteria);

        Posts post = mongoTemplate.findOne(query, Posts.class, "Posts");
//        Users user = mongoTemplate.findById(userEmail, Users.class);
        return Optional.ofNullable(post).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not found"));
    }
}
