package com.petstagram.data;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String username;
    private final boolean status;

    public JwtResponse(String jwttoken, String username, boolean status) {
        this.jwttoken = jwttoken;
        this.username = username;
        this.status = status;
    }

    public String getToken() {
        return this.jwttoken;
    }
    
    public String getUsername() {
    	return this.username;
    }
    
    public boolean getStatus() {
    	return this.status;
    }
}
