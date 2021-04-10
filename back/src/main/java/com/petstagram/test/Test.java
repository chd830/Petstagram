package com.petstagram.test;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection="Test")
public class Test {
    @Id
    private String _id;

//    @Field("userName")
    private String userName;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
