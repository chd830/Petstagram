package com.petstagram.data;

import lombok.*;

// Getter생성
@Getter
// Setter생성
@Setter
// public Users() {} 생성
@NoArgsConstructor
// public Users(String userEmail, String userNickname..) {} 생성
@AllArgsConstructor
// ToString생성
@ToString
public class Users {
    private String userEmail;
    private String userNickname;
    private String userPwd;
    private int userAge;
    private String userImg;
    private boolean isPublic;
}
