package com.petstagram.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String username;
    private final int userAge;
    private final String userImg;

    public JwtResponse(String jwttoken, String username, int userAge, String userImg) {
        this.jwttoken = jwttoken;
        this.username = username;
        this.userAge = userAge;
        this.userImg = userImg;
    }
}
