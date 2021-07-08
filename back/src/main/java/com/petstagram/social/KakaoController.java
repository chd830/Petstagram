//package com.petstagram.social;
//import java.io.IOException;
//import java.util.Random;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.codehaus.jackson.JsonNode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.webgame.config.JwtTokenUtil;
//import com.ssafy.webgame.dto.Users;
//import com.ssafy.webgame.service.JwtUserDetailsService;
//import com.ssafy.webgame.service.UserService;
//
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("/api")
//public class KakaoController {
//
//	static private final String client_id = "caa62dc8f7e146b75add64f07e587b87";
//	static private final String client_secret = "npFLHw8MTgQGodRs9O5GDI4MgLI6iO2h";
//	static private final String redirect_uri = "https://k3c104.p.ssafy.io/api/kakaocallback";
//
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private PasswordEncoder bcryptEncoder;
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//	@Autowired
//	private JwtUserDetailsService userDetailsService;
//
//	@CrossOrigin
//	@ApiOperation(value="카카오 로그인 api링크 주소 생성")
//	@RequestMapping(value = "/kakaologin", method = {RequestMethod.GET, RequestMethod.POST})
//	public Object kakaologin(Model model, HttpSession session) {
//		String url = "https://kauth.kakao.com/oauth/authorize?client_id="
//				+ client_id
//				+ "&redirect_uri="
//				+ redirect_uri
//				+ "&response_type=code&client_secret="
//				+ client_secret;
//		System.out.println(url);
//		model.addAttribute("url", url);
//		return new ResponseEntity<>(url, HttpStatus.OK);
//	}
//
//	@ApiOperation(value="카카오 로그인 성공시 callback 호출")
//	@RequestMapping(value = "/kakaocallback", method = {RequestMethod.GET, RequestMethod.POST})
//	public void kakao(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		System.out.println(code);
//
//		JsonNode accessToken;
//
//		JsonNode jsonToken = KakaoAccessToken.getKakaoAccessToken(code);
//		accessToken = jsonToken.get("access_token");
//
//		System.out.println("access_token : " + accessToken);
//
//		// access_token을 통해 사용자 정보 요청
//        JsonNode userInfo = KakaoUserInfo.getKakaoUserInfo(accessToken);
//        System.out.println(userInfo);
//
//        // Get id
//        String id = userInfo.path("id").asText();
//        String name = null;
//        String email = null;
//
//        // 유저정보 카카오에서 가져오기 Get properties
//        JsonNode properties = userInfo.path("properties");
//        JsonNode kakao_account = userInfo.path("kakao_account");
//
//        name = properties.path("nickname").asText();
//        email = kakao_account.path("email").asText();
//
//        System.out.println("id : " + id);
//        System.out.println("name : " + name);
//        System.out.println("email : " + email);
//
//        String url = "";
//
//        Users user = userService.selectUserByUseremail(email);
//		System.out.println(user);
//
//		if(user != null) {
//			// user 존재
//			final UserDetails userDetails = userDetailsService
//                    .loadUserByUsername(user.getUseremail());
//			String loginToken = jwtTokenUtil.generateToken(userDetails);
//        	System.out.println(loginToken);
//        	url = "https://k3c104.p.ssafy.io/intermediate?token="+loginToken+"&useremail="+email;
//		}
//		else {
//			// user 등록
//			user = new Users();
//			user.setUseremail(email);
//			user.setUsernickname(name);
//			Random random = new Random();
//			String pass = Integer.toString(random.nextInt(900000) + 100000);
//			System.out.println("패스워드:"+pass);
//			user.setUserpwd(bcryptEncoder.encode(pass));
//			userService.insertUser(user);
//			// 토큰 생성
//			final UserDetails userDetails = userDetailsService
//                    .loadUserByUsername(user.getUseremail());
//			String loginToken = jwtTokenUtil.generateToken(userDetails);
//        	System.out.println(loginToken);
//
//        	url = "https://k3c104.p.ssafy.io/intermediate?token="+loginToken+"&useremail="+email+"&password="+pass;
////			url = "http://localhost:3000/login";
//		}
//		// 리다이렉트
//		response.sendRedirect(url);
//
//	}
//}
