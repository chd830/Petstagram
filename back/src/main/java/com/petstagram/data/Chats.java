package com.petstagram.data;

import lombok.*;

// Getter생성
@Getter
// Setter생성
@Setter
// public Chats() {} 생성
@NoArgsConstructor
// public Chats(int chatNo, int chatRoomNo..) {} 생성
@AllArgsConstructor
// ToString생성
@ToString
public class Chats {
    private int chatNo;
    private int chatRoomNo;
    private String chatUserEmail;
    private String chatImg;
    private String chatMsg;
    private String chatCreated;
    private boolean read;
}
