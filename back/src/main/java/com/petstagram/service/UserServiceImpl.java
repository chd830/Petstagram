package com.petstagram.service;

import com.petstagram.data.Users;
import com.petstagram.test.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class UserServiceImpl implements UserService {

    // autowired가 안됨 ㅎㅎ.. sender의 값이 null로 들어감
    @Autowired
    private JavaMailSender sender;

    private MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://127.0.0.1:27017/test"));

    // INSERT
    @Override
    public Users insert(Users user) {
        return mongoTemplate.insert(user);
    }

    // SELECT
    @Override
    public Users getUsers(Users user) {
        List<Users> list = getAllUsers();

        System.out.println("USER LIST");
        for(Users u : list) {
            if(u.getUserEmail().equals(user.getUserEmail()))
                return Optional.ofNullable(u).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not fount"));
        }
        return null;
//        Users u = mongoTemplate.findById(user.getUserEmail(), Users.class);
//        return Optional.ofNullable(u).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "not fount"));
    }

    @Override
    public List<Users> getAllUsers() {
        return mongoTemplate.findAll(Users.class);
    }

    @Override
    public boolean checkSignIn(Users user) {
        return this.getUsers(user).getUserPwd().equals(user.getUserPwd());
    }
    // UPDATE

    @Override
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

    /*
     * 현재시간과 비교해서 만료시간이 지났는지 확인
     * parameter type: expiredate(String)
     * return type: int(-1: 만료됨. 0~1: 만료되지않음)
     */
    public int compare(String expiredate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(expiredate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar now = Calendar.getInstance();
        //만료일과 현재 시간을 비교해서 만료일이 지나면 음수를 반환
        return cal.compareTo(now);
    }

    /*
     * 이메일로 인증키를 보내고 DB에 저장하는 기능
     * parameter type: useremail
     * return type: void
     */
    public void send(String email) {
        try {
            System.out.println("MAIL INFO");
            SimpleMailMessage message = new SimpleMailMessage();
            int random = (int)(Math.random()*1000000);
            //받는사람
            message.setTo(email);
            //메세지 내용
            message.setText(email+"님의 인증번호는 "+random+"입니다.");
            //메세지가 보내지는 시간설정
            TimeZone time = TimeZone.getDefault();
            Calendar cal = new GregorianCalendar(time);
            message.setSentDate(cal.getTime());
            //DB에 저장되는 이메일과 이메일키, 등록시간과 만료시간.
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.add(cal.MINUTE, 3);
            //이메일과 인증키를 DB에 저장
            System.out.println(message);
            sender.send(message);
        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /*
     * 이메일을 보낼 때 성공했는지를 판별하는 기능
     * parameter type: useremail
     * return type: boolean
     */
    @Override
    public boolean sendMail(Users user) {
        try {
            send(user.getUserEmail());
            return true;
        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
}