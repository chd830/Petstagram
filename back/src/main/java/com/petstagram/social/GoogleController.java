package com.petstagram.social;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.petstagram.configuration.JwtTokenUtil;
import com.petstagram.data.Role;
import com.petstagram.data.Token;
import com.petstagram.data.Users;
import com.petstagram.service.JwtUserDetailsService;
import com.petstagram.service.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class GoogleController {
	
	static private final String client_id = "547900846133-ioo7t5i7eeal0b62uqshq2bje70g2hhi.apps.googleusercontent.com";
	static private final String client_secret = "1BIsFcGsIEr7VnXjqnJT7nro";
	static private final String redirect_uri = "http://localhost:8000/api/v1/auth/google/callback";
	
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@CrossOrigin
	@RequestMapping(value = "/api/v1/googlelogin", method = {RequestMethod.GET, RequestMethod.POST})
	public Object google(Model model, HttpSession session) {
		String url = "https://accounts.google.com/o/oauth2/auth?client_id="+
			    	client_id +
				"&redirect_uri="+
			      redirect_uri +
			      "&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email&approval_prompt=force&access_type=offline";
		System.out.println(url);
		model.addAttribute("url", url);
		return new ResponseEntity<>(url, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/v1/auth/google/callback", method = {RequestMethod.GET, RequestMethod.POST})
	public void googlecallback(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		System.out.println("CALL BACK");
		String code = request.getParameter("code");
		String query = "code=" + code;
		query += "&client_id=" + client_id;
		query += "&client_secret=" + client_secret;
		query += "&redirect_uri=" + redirect_uri;
		query += "&grant_type=authorization_code";

		String tokenJson = getHttpConnection("https://accounts.google.com/o/oauth2/token", query);
		Gson gson = new Gson();
		Token token = gson.fromJson(tokenJson, Token.class);
		System.out.println("TOKEN JSON: "+token);

		String ret = getHttpConnection("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token.getAccess_token());
		System.out.println("RET: "+ret);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(ret);
		JSONObject jsonobj = (JSONObject) obj;
		System.out.println("JOSNOBJ: "+jsonobj);
		String id = (String) jsonobj.get("id");
		String email = (String) jsonobj.get("email");
		String name = (String) jsonobj.get("name");
		System.out.println(id);
		System.out.println(email);
		System.out.println(name);
		System.out.println(email.split("@")[0]);
		
		String url = "";
		Users getUser = new Users();
		getUser.setUserEmail(email);
		Users user = userService.getUsers(getUser);
		System.out.println("USER: "+user);
		
		if(user != null) {
			final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(user.getUserEmail());
			String loginToken = jwtTokenUtil.generateToken(userDetails);
        	System.out.println(loginToken);
        	url = "http://localhost:8080/intermediate?token="+loginToken+"&useremail="+email;
		}
		else {
			// user 등록
			user = new Users();
			user.setUserEmail(email);
			// 구글은 name을 반환 안해준다.
			Random random = new Random();
			String pass = Integer.toString(random.nextInt(900000) + 100000);
			System.out.println("패스워드:"+pass);
			user.setUserNickname(email.split("@")[0]);
			user.setRole(Role.USER);
			user.setUserPwd(bcryptEncoder.encode(pass));
			userService.insert(user);
			// 토큰 생성
			final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(user.getUserEmail());
			String loginToken = jwtTokenUtil.generateToken(userDetails);
        	System.out.println(loginToken);

        	url = "http://localhost:8080/intermediate?token="+loginToken+"&useremail="+email+"&password="+pass;
//			url = "http://localhost:3000/login";
		}
		response.sendRedirect(url);
	}
	
	private String getHttpConnection(String uri) throws IOException {
		URL url = new URL(uri);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		int responseCode = conn.getResponseCode();
		System.out.println(responseCode);

		String line;
		StringBuffer buffer = new StringBuffer();
		try (InputStream stream = conn.getInputStream()) {
			try (BufferedReader rd = new BufferedReader(new InputStreamReader(stream))) {
				while ((line = rd.readLine()) != null) {
					buffer.append(line);
					buffer.append('\r');
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private String getHttpConnection(String uri, String param) throws IOException {
		URL url = new URL(uri);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		try (OutputStream stream = conn.getOutputStream()) {
			try (BufferedWriter wd = new BufferedWriter(new OutputStreamWriter(stream))) {
				wd.write(param);
			}
		}
		int responseCode = conn.getResponseCode();
		System.out.println(responseCode);

		String line;
		StringBuffer buffer = new StringBuffer();
		try (InputStream stream = conn.getInputStream()) {
			try (BufferedReader rd = new BufferedReader(new InputStreamReader(stream))) {
				while ((line = rd.readLine()) != null) {
					buffer.append(line);
					buffer.append('\r');
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
