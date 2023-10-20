package com.blog.payload;

import java.io.Serializable;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5926468583005150707L;
	private String usernameOrEmail;
	private String password;
	
}