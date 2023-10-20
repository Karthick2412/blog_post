package com.blog.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.security.CustomeUserDetailService;
import com.blog.utils.JwtToken;

import io.jsonwebtoken.ExpiredJwtException;

//@Component
//@Profile(Profiles.JWT_AUTH)
//@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtToken jwtToken;
	@Autowired
	private CustomeUserDetailService userDetailService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenHeader=request.getHeader("Authorization");
		String userName=null;
		String token=null;
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			token=tokenHeader.substring(7);
			try {
			userName=jwtToken.getNameFromToken(token);
			}catch(IllegalArgumentException ex) {
				System.out.println("Unable to get Jwt Token");
			}catch(ExpiredJwtException ex) {
				System.out.println("JWT token has EXPIRED");
			}
			
		}
//		else {
//			logger.warn("Token doesn't start with BEARER");
//		}
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=userDetailService.loadUserByUsername(userName);
			if(jwtToken.ValidateToken(token, userDetails)) {
				//ask
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
}