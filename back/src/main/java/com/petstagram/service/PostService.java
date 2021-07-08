package com.petstagram.service;

import com.petstagram.data.Posts;
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

    public Posts getBypostNo(int postNo){
        try{
            Criteria criteria = new Criteria("postNo");
            criteria.is(postNo);

            Query query = new Query(criteria);

            Posts post = mongoTemplate.findOne(query, Posts.class, "Posts");

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
            // auto_increment
            List<Posts> post = getAll();
            int no;
            if (post.size() > 0){
                no = post.get(post.size()-1).getPostNo();
            } else {
                no = 0;
            }
            posts.setPostNo(no+1);

            mongoTemplate.insert(posts);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // post 수정
    public boolean update(Posts posts){
        try{
            Criteria criteria = new Criteria("postNo");
            criteria.is(posts.getPostNo());

            Query query = new Query(criteria);
            Update update = new Update();

            update.set("postContent", posts.getPostContent());
            update.set("postImg", posts.getPostImg());
            update.set("postUpdateDate", posts.getPostUpdateDate());
            update.set("categoryName", posts.getCategoryName());
            update.set("hashtagContent", posts.getHashtagContent());
            update.set("tagUserEmail", posts.getTagUserEmail());

            mongoTemplate.updateFirst(query, update, Posts.class);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // postLike 수정
    public boolean updatePostLike(Posts posts){
        try{
            Criteria criteria = new Criteria("postNo");
            criteria.is(posts.getPostNo());

            Query query = new Query(criteria);
            Update update = new Update();

            update.set("postLike", posts.getPostLike());

            mongoTemplate.updateFirst(query, update, Posts.class);
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
}
