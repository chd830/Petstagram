//package com.petstagram.social;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URISyntaxException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.json.simple.JSONObject;
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
//import com.github.scribejava.core.model.OAuth2AccessToken;
//import com.ssafy.webgame.config.JwtTokenUtil;
//import com.ssafy.webgame.dto.Users;
//import com.ssafy.webgame.service.JwtUserDetailsService;
//import com.ssafy.webgame.service.UserService;
//
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("/api")
//public class NaverController {
//	@Autowired
//    private NaverLoginBO naverLoginBO;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private PasswordEncoder bcryptEncoder;
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//	@Autowired
//	private JwtUserDetailsService userDetailsService;
//    private String apiResult = null;
//    private JsonStringParse jsonparse = new JsonStringParse();
//
//
//    @Autowired
//    private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
//        this.naverLoginBO = naverLoginBO;
//    }
//
//    //로그인 첫 화면 요청 메소드
//    @CrossOrigin
//    @ApiOperation(value="네이버 api주소 링크 생성")
//    @RequestMapping(value = "/naverlogin", method = { RequestMethod.GET, RequestMethod.POST })
//    public Object login(Model model, HttpSession session) {
//
//        /* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
//        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
//
//        //https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
//        //redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
//        System.out.println("네이버:" + naverAuthUrl);
//
//        //네이버
//        model.addAttribute("url", naverAuthUrl);
//
//        /* 생성한 인증 URL을 View로 전달 */
//        return new ResponseEntity<>(naverAuthUrl, HttpStatus.OK);
//    }
//
//    //네이버 로그인 성공시 callback호출 메소드
//    @ApiOperation(value="네이버 로그인 성공시 callback 호출")
//    @RequestMapping(value = "/navercallback", method = { RequestMethod.GET, RequestMethod.POST })
//    public void callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session, HttpServletResponse response)
//            throws IOException, URISyntaxException, ServletException {
//    	HttpURLConnection con;
//        System.out.println("여기는 callback");
//        OAuth2AccessToken oauthToken;
//        oauthToken = naverLoginBO.getAccessToken(session, code, state);
//        System.out.println(oauthToken.getAccessToken());
//        String token = oauthToken.getAccessToken();
//        String header = "Bearer " + token; // Bearer 다음에 공백 추가
//
//        String apiURL = "https://openapi.naver.com/v1/nid/me";
//
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("Authorization", header);
//        String responseBody = NaverLoginBO.get(apiURL,requestHeaders);
//
//        System.out.println("testw");
//        System.out.println(responseBody);
//
//        //로그인 사용자 정보를 읽어온다.
//        apiResult = naverLoginBO.getUserProfile(oauthToken);
//        System.out.println(naverLoginBO.getUserProfile(oauthToken).toString());
//        model.addAttribute("result", apiResult);
//        System.out.println("result"+apiResult);
//        /* 네이버 로그인 성공 페이지 View 호출 */
////      JSONObject jsonobj = jsonparse.stringToJson(apiResult, "response");
////      String snsId = jsonparse.JsonToString(jsonobj, "id");
////      String name = jsonparse.JsonToString(jsonobj, "name");
////
////      UserVO vo = new UserVO();
////      vo.setUser_snsId(snsId);
////      vo.setUser_name(name);
////
////      System.out.println(name);
////      try {
////          vo = service.naverLogin(vo);
////      } catch (Exception e) {
////          // TODO Auto-generated catch block
////          e.printStackTrace();
////      }
//
//        JSONObject jsonobj = jsonparse.stringToJson(apiResult, "response");
//        String naverid = jsonparse.JsonToString(jsonobj, "id");
//        String name = jsonparse.JsonToString(jsonobj, "name");
//        String email = jsonparse.JsonToString(jsonobj, "email");
//
////        User user = new User();
////        user.setNickname(name);
////        user.setEmail(email);
//        System.out.println(name);
//        System.out.println(email);
//
//        // id에 해당하는 user가 존재하는지 확인
//        String url = "";
//        Users user = userService.selectUserByUseremail(email);
//        System.out.println(user);
//        // 있으면(등록되어 있는 사람) -> 로그인 처리 후 main페이지로 이동
//        if(user != null) {
//        	// 로그인 처리가 session에 안 담김 -> 토큰 처리해서 가져와서 프론트로 보내기
//        	final UserDetails userDetails = userDetailsService
//                    .loadUserByUsername(user.getUseremail());
//        	String loginToken = jwtTokenUtil.generateToken(userDetails);
//        	System.out.println("토큰:"+loginToken);
////        	url = new URL("http://localhost:8080/#/");
////        	con = (HttpURLConnection) url.openConnection();
////        	con.setInstanceFollowRedirects(false);
//        	url = "https://k3c104.p.ssafy.io/intermediate?token="+loginToken+"&useremail="+email;
//        }
//        // 등록이 안 되어있으면
//        else {
//        	// user 등록
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
//        	String loginToken = jwtTokenUtil.generateToken(userDetails);
//        	System.out.println("토큰:"+loginToken);
//
//        	url = "https://k3c104.p.ssafy.io/intermediate?token="+loginToken+"&useremail="+email+"&password="+pass;
////			url = "http://localhost:3000/login";
//        }
//        response.sendRedirect(url);
//    }
//}
