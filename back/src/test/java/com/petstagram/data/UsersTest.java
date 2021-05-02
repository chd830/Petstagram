package com.petstagram.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersTest {
    @Test
    public void lombokTest() {
        String userEmail = "chd830@naver.com";
        String userNickname = "chong";
        String userPwd = "password";
        int userAge = 26;
        boolean isPublic = false;

//        Users user = new Users(userEmail, userNickname, userPwd, userAge, "", isPublic);

        // assertThat: 테스트 검정 라이브러리
//        assertThat(user.getUserEmail()).isEqualTo(userEmail);
//        assertThat(user.getUserAge()).isEqualTo(userAge);
    }
}
