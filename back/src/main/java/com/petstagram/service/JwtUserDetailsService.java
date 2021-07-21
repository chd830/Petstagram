package com.petstagram.service;

import java.util.ArrayList;

import com.petstagram.data.Role;
import com.petstagram.data.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	/**
	 * email 정보로 user 데이터 가져오기(jwt 토큰 생성을 위한 메소드)
	 * parameter type: String email
	 * return type: UserDetail(spring에서 지원하는 User type)
	 */
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users getUser = new Users();
		getUser.setUserEmail(email);
		Users user = userService.getUsers(getUser);
		user.setRole(Role.USER);
		System.out.println("DETAIL SERVICE: "+user);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
        } else {
        	return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getUserPwd(),
                    new ArrayList<>());
        }
    }
	
	/**
	 * bcrypt로 패스워드 인코딩 작업 후 회원등록을 위한  save 메소드
	 * parameter type: User type
	 * return type: boolean
	 */
	public boolean save(Users user) {
		System.out.println("SAVE: "+user);
		Users newUser = userService.getUsers(user);
		if(newUser != null) {
			newUser.setUserPwd(bcryptEncoder.encode(user.getUserPwd()));
			return userService.updateUsers(newUser);
		} else {
			user.setUserPwd(bcryptEncoder.encode(user.getUserPwd()));
			user.setRole(Role.USER);
			userService.insert(user);
			return true;
		}
	}
}
