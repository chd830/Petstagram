package com.petstagram.security.oauth2.user;


import com.petstagram.data.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "oAuth2Id", "email", "nickname", "introduction", "role"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String oAuth2Id;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String profileImageUrl;

    private String introduction;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

}
