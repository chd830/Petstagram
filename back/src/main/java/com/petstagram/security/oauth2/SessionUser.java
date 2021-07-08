package com.petstagram.security.oauth2;

import com.petstagram.data.Users;

import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users user) {
        this.name = user.getUserNickname();
        this.email = user.getUserEmail();
        this.picture = user.getUserImg();
    }
}
