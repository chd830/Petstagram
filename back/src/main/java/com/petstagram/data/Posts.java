package com.petstagram.data;

import lombok.*;

// Getter생성
@Getter
// Setter생성
@Setter
// public Posts() {} 생성
@NoArgsConstructor
// public Posts(int postNo, int categoryNo..) {} 생성
@AllArgsConstructor
// ToString생성
@ToString
public class Posts {
    private int postNo;
    private int categoryNo;
    private String postSubject;
    private String postContent;
    private int postLike;
    private String postImg;
    private double postLng;
    private double postLat;
    private String userEmail;
    private String postCreateDate;
    private String postUpdateDate;
}
