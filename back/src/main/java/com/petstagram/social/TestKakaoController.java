//package com.petstagram.social;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Random;
//
//import com.petstagram.configuration.JwtTokenUtil;
//import com.petstagram.data.Role;
//import com.petstagram.data.Users;
//import com.petstagram.service.JwtUserDetailsService;
//import com.petstagram.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import javax.servlet.http.HttpServletResponse;
//
//@RestController
//public class TestKakaoController {
//
//    static private final String client_id = "3c33c863743730c6fb632e42eb9fda50";
//    static private final String client_secret = "aaGrKB3Y9Pgh30cFCWT7z9Y2UbPLeGSA";
//    static private final String redirect_uri = "http://localhost:8000/api/v1/auth/kakao/callback";
//    static private final String rest_api_key = "3c33c863743730c6fb632e42eb9fda50";
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//    @Autowired
//    private PasswordEncoder bcryptEncoder;
//    @Autowired
//    private JwtUserDetailsService userDetailsService;
//
//    @RequestMapping(value="/")
//    public String index() {
//
//        return "index";
//    }
//
//    @RequestMapping("/api/v1/auth/kakao/callback")
//    public void login(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
//        String access_Token = getAccessToken(code);
//        Users getUser = getUserInfo(access_Token);
//        String email = getUser.getUserEmail();
//        Users user = userService.getUsers(getUser);
//        String url = "";
//
//        if(user != null) {
//            final UserDetails userDetails = userDetailsService
//                    .loadUserByUsername(user.getUserEmail());
//            String loginToken = jwtTokenUtil.generateToken(userDetails);
//            System.out.println(loginToken);
//            url = "http://localhost:8080/intermediate?token="+loginToken+"&useremail="+email;
//        }
//        else {
//            // user 등록
//            user = new Users();
//            user.setUserEmail(email);
//            // 구글은 name을 반환 안해준다.
//            Random random = new Random();
//            String pass = Integer.toString(random.nextInt(900000) + 100000);
//            System.out.println("패스워드:"+pass);
//            user.setUserNickname(email.split("@")[0]);
//            user.setRole(Role.USER);
//            user.setUserPwd(bcryptEncoder.encode(pass));
//            System.out.println("INSERT USER");
//            userService.insert(user);
//            System.out.println("GET USER");
//            System.out.println(userService.getUsers(user));
//            // 토큰 생성
//            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserEmail());
//            String loginToken = jwtTokenUtil.generateToken(userDetails);
//            System.out.println(loginToken);
//
//            url = "http://localhost:8080/intermediate?token="+loginToken+"&useremail="+email+"&password="+pass;
//        }
//        response.sendRedirect(url);
//    }
//
//
//    public String getAccessToken (String authorize_code) throws UnsupportedEncodingException {
//        String access_Token = "";
//        String refresh_Token = "";
//        String reqURL = "https://kauth.kakao.com/oauth/token";
//
//        try {
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            StringBuilder sb = new StringBuilder();
//            sb.append("grant_type=authorization_code");
//            sb.append("&client_id="+client_id); //수정 할것
//            sb.append("&redirect_uri="+redirect_uri); //수정 할것
//            sb.append("&client_secret="+client_secret); //수정 할것
//            sb.append("&code=" + authorize_code);
//            bw.write(sb.toString());
//            bw.flush();
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//            String result = "";
//
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//            System.out.println("response body : " + result);
//
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);
//
//            access_Token = element.getAsJsonObject().get("access_token").getAsString();
//            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
//
//            System.out.println("access_token : " + access_Token);
//            System.out.println("refresh_token : " + refresh_Token);
//
//            br.close();
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return access_Token;
//    }
//
//    public Users getUserInfo (String access_Token) {
//        Users user = new Users();
//        String reqURL = "https://kapi.kakao.com/v2/user/me";
//        try {
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            String line = "";
//            String result = "";
//
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//            System.out.println("response body : " + result);
//
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);
//
//            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
//
//            String email = kakao_account.getAsJsonObject().get("email").getAsString();
//
//            user.setUserEmail(email);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return user;
//    }
//
//}