package com.petstagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstagram.data.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;

import static org.hamcrest.Matchers.is; // 수동으로 추가해줌.
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
// Junit테스트에서 SpringBoot 테스트 기능을 사용할 때 붙이는 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
// 컨트롤러 어노테이션을 사용한다는 선언
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    //  웹 API 테스트시 사용
    @Autowired
    private MockMvc mvc;

    static Users user = new Users("ab", "chong", "ff", 26, "", false, new ArrayList<>());
    static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void returnUser() throws Exception {
        mvc.perform(post("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(user)))
                // status 검증. 잘 수행되면 200
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                // 내용 검증
                .andExpect(jsonPath("$.userEmail", is(user.getUserEmail())))
                .andExpect(jsonPath("$.userNickname", is("cd")))
                .andDo(print());
    }

    @Test
    public void signin() throws Exception {
        mvc.perform(post("/api/v1/user/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
