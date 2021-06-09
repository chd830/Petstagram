package com.petstagram.security.oauth2.user;

import com.petstagram.data.Users;
import lombok.Getter;

import java.io.Serializable;

@Getter
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