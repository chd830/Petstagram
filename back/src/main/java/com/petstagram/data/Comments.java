package com.petstagram.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Document(collection = "Comments")
// Getter생성
@Getter
// Setter생성
@Setter
// public Comments() {} 생성
@NoArgsConstructor
// public Comments(int commentNo, ..) {} 생성
@AllArgsConstructor
// ToString생성
@ToString
public class Comments {
    @Id
    private int commentNo;
    private String commentContent;
    private String commentCreateDate;
    private String commentUpdateDate;
    private List<String> commentLike;

    // Users
    private String userEmail;

//    Posts
    private int postNo;
}
