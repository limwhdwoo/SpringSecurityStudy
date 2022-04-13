package com.ljw.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled =true)  //@secured 어노테이션 활성화 개별적으로 권한설정 가능
//prePostEnabled=true 할 경우 @PreAuthorize("hasRole('ROLE_USER')or hasRole('ROLE_MANAGER' ")형식으로 
//여러개 권한설정 가능 여러개 권한설정 할 경우 사용

public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//해당 매서드 리턴되는 오브젝트를 ioc로 등록해준다. // autowired해서 어디서든 쓸 수 있다~!
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() //로그인 사용자만 이용 가능
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login") //해당 주소가 호출되면 시큐리티가 대신 로그인 진행
			.defaultSuccessUrl("/");
	}
	
}
