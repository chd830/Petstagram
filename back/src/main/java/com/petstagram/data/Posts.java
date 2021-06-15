package com.petstagram.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Document(collection = "Posts")
// Getter생성
@Getter
// Setter생성
@Setter
// public Posts() {} 생성
@NoArgsConstructor
// public Posts(int postNo, ..) {} 생성
@AllArgsConstructor
// ToString생성
@ToString
public class Posts {
    @Id
    private int postNo;
    private String postSubject;
    private String postContent;
    private List<String> postLike;
    private String postImg;
    private double postLng;
    private double postLat;
    private String postCreateDate;
    private String postUpdateDate;

    // Comments
//    delete?
    private List<Integer> commentNo;

    // Categories
    private String categoryName;

    // HashTags
    private List<String> hashtagContent;

    // PostTags
    private List<String> tagUserEmail;

    // Users
    private String userEmail;
}