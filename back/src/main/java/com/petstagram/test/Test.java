package com.petstagram.test;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection="Test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Test {
    @Id
    private String _id;
//    @Field("userName")
    private String userName;
}
