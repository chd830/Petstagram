package com.petstagram.service;

import com.petstagram.data.Comments;
import com.petstagram.test.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.CacheRequest;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Comments> getAll() {
        try{
            List<Comments> comments = mongoTemplate.findAll(Comments.class);
            return Optional.ofNullable(comments).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not found"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Comments getBycommentNo(int commentNo){
        try{
            Criteria criteria = new Criteria("commentNo");
            criteria.is(commentNo);

            Query query = new Query(criteria);

            Comments comments = mongoTemplate.findOne(query, Comments.class, "Comments");
            return Optional.ofNullable(comments).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not found"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Comments> getByuserEmail(String userEmail){
        try{
            List<Comments> comments = mongoTemplate.find(new Query().addCriteria(Criteria.where("userEmail").is(userEmail)), Comments.class);
            return comments;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Comments comments){
        try{
            mongoTemplate.insert(comments);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Comments comments){
        try{
            Criteria criteria = new Criteria("commentNo");
            criteria.is(comments.getCommentNo());
            Query query = new Query(criteria);
            Update update = new Update();

            update.set("commentContent", comments.getCommentContent());
            update.set("commentUpdateDate", comments.getCommentUpdateDate());
            update.set("commentLike", comments.getCommentLike());


            mongoTemplate.updateFirst(query, update, Comments.class);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
