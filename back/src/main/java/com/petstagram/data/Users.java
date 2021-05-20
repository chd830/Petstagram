package com.petstagram.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

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
@Entity
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
    private String friendUserEmail;
}
