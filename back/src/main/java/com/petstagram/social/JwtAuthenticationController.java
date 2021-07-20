package com.petstagram.social;

import com.petstagram.configuration.JwtTokenUtil;
import com.petstagram.data.JwtRequest;
import com.petstagram.data.JwtResponse;
import com.petstagram.data.Users;
import com.petstagram.service.JwtUserDetailsService;
import com.petstagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private UserService userService;

	@RequestMapping(value="/api/v1/user/getuser", method = RequestMethod.POST)
	public ResponseEntity<?> getUser(@RequestBody Users user) throws Exception {
		return new ResponseEntity<Users>(userService.getUsers(user), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/v1/user/signin", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		System.out.println(authenticationRequest);
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getEmail());
		Users getUSer = new Users();
		getUSer.setUserEmail(authenticationRequest.getEmail());
        Users user = userService.getUsers(getUSer);
        if(user != null) {
        	final String loginToken = jwtTokenUtil.generateToken(userDetails);
            
            System.out.println("이메일:"+jwtTokenUtil.getEmailByToken(loginToken));
            System.out.println("닉네임:"+jwtTokenUtil.getNicknameByToken(loginToken));

			return ResponseEntity.ok(new JwtResponse(loginToken, jwtTokenUtil.getNicknameByToken(loginToken), user.getUserAge(), user.getUserImg()));
        } else {
        	return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }
	
	@RequestMapping(value = "/api/v1/user/signup", method = RequestMethod.POST)
	public ResponseEntity<Boolean> saveUser(@RequestBody Users user) throws Exception {
		try {
			boolean check = userDetailsService.save(user);
			return new ResponseEntity<>(check, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
			System.out.println("DISABLED");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
			System.out.println("INVALID");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}