package com.petstagram.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is; // 수동으로 추가해줌.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Junit테스트에서 SpringBoot 테스트 기능을 사용할 때 붙이는 어노테이션
@RunWith(SpringRunner.class)
// 컨트롤러 어노테이션을 사용한다는 선언
@WebMvcTest(controllers = MainController.class)
public class MainControllerTest {

    //  웹 API 테스트시 사용
    @Autowired
    private MockMvc mvc;

    @Test
    public void returnMain() throws Exception {
        String main = "index";
        mvc.perform(get("/"))
                // status 검증. 잘 수행되면 200
                .andExpect(status().isOk())
                // 내용 검증
                .andExpect(content().string(main));
    }

//    @Test
//    public void returnUser() throws Exception {
//        String userEmail = "chd830@naver.com";
//        String userNickname = "chong";
//        String userPwd = "password";
//        int userAge = 26;
//        boolean isPublic = false;
//
//        mvc.perform(get("/user")
//                .param("userEmail", userEmail)
//                .param("userNickname", userNickname)
//                .param("userPwd", userPwd)
//                .param("userImg", "")
//                .param("userAge", String.valueOf(userAge))
//                .param("isPublic", String.valueOf(isPublic)))
//                .andExpect(status().isOk())
//                // json응답을 필드별로 검증할 수 있는 메소드
//                .andExpect(jsonPath("$.userEmail", is(userEmail)))
//                .andExpect(jsonPath("$.userNickname", is(userNickname)));
//
//    }
}
