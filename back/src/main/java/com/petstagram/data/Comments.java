package com.petstagram.data;

import lombok.*;

// Getter생성
@Getter
// Setter생성
@Setter
// public Comments() {} 생성
@NoArgsConstructor
// public Comments(int commentNo, int postNo..) {} 생성
@AllArgsConstructor
// ToString생성
@ToString
public class Comments {
    private int commentNo;
    private int postNo;
    private String userEmail;
    private String commentContent;
    private String commentCreateDate;
    private String commentUpdateDate;
    private int commentLike;
}
