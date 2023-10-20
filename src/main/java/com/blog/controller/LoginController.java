package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.LoginDTO;
import com.blog.utils.JwtToken;



@RestController
@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
public class LoginController {
	@Autowired
	private JwtToken jwtToken;
	@Autowired
	private AuthenticationManager authenticateManager;
	
	@PostMapping("/signin")
	public String authenticateUser(@RequestBody LoginDTO loginDTO){
		Authentication authentication=authenticateManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		UserDetails userDetails= (UserDetails) authentication.getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtToken.TokenGeneration(userDetails);
	}
}
