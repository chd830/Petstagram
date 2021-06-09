package com.petstagram.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Document(collection = "users")
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
@XmlRootElement
public class Users {
    @Id
    private String userEmail;
    private String userNickname;
    private String userPwd;
    private int userAge;
    private String userImg;
    private boolean isPublic;

    // Friends
    private List<String> friendUserEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public String getRoleKey() {
        return this.role.getKey();
    }
//    @Builder
//    public Users(String userEmail, String userNickname, String userPwd, int userAge, String userImg, boolean isPublic, List<String> friendUserEmail, Role role) {
//        this.userEmail = userEmail;
//        this.userNickname = userNickname;
//        this.userPwd = userPwd;
//        this.userAge = userAge;
//        this.userImg = userImg;
//        this.isPublic = isPublic;
//        this.friendUserEmail = friendUserEmail;
//        this.role = role;
//    }
}
