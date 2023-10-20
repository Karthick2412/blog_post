package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blog.filter.JwtFilter;
import com.blog.security.CustomeUserDetailService;
import com.blog.security.JwtAuthenticationEntryPoint;



@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomeUserDetailService userDetailService;
	

//	@Autowired
//	private JwtFilter jwtFilter;
	
	@Bean
    public JwtFilter jwtAuthenticationFilter(){
        return  new JwtFilter();
    }
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
		//auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	  
	  @Override
	  @Bean
	  public AuthenticationManager authenticationManagerBean() throws Exception {
		  return super.authenticationManagerBean(); }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//		.authorizeRequests()
//		//.antMatchers("/api/auth/**").permitAll()
//		.antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//		.antMatchers("/api/posts**").hasAnyRole("ADMIN","USER")
//		.and().authorizeRequests().anyRequest().permitAll()
//		.and().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
//        .and().sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		 http
         .csrf().disable()
         .exceptionHandling()
         .authenticationEntryPoint(jwtAuthEntryPoint)
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .authorizeRequests()
         .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
         .antMatchers("/api/v1/auth/**").permitAll()
         .antMatchers("/v2/api-docs/**").permitAll()
         .antMatchers("/swagger-ui/**").permitAll()
         .antMatchers("/swagger-resources/**").permitAll()
         .antMatchers("/swagger-ui.html").permitAll()
         .antMatchers("/webjars/**").permitAll()
         .anyRequest()
         .authenticated();
		 http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
//		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll()
//		.antMatchers("/api/auth/**").permitAll()
//		.anyRequest().authenticated()
//        .and().exceptionHandling().and().sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	  
	  
	  
		/*
		 * @Override
		 * 
		 * @Bean protected UserDetailsService userDetailsService() { UserDetails
		 * karthick=User.builder().username("karthick").password(passwordEncoder().
		 * encode("user1")).roles("USER").build(); UserDetails
		 * admin=User.builder().username("admin").password(passwordEncoder().encode(
		 * "admin")).roles("ADMIN").build(); return new
		 * InMemoryUserDetailsManager(karthick,admin); }
		 */

}

