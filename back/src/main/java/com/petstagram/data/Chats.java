package com.petstagram.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Document(collection = "Chats")
// Getter생성
@Getter
// Setter생성
@Setter
// public Chats() {} 생성
@NoArgsConstructor
// public Chats(int chatNo, ..) {} 생성
@AllArgsConstructor
// ToString생성
@ToString
@Entity
public class Chats {
    @Id
    private int chatNo;

    private String chatImg;
    private String chatUserEmail;
    private String chatMsg;
    private String chatCreated;
    private boolean read;
}
