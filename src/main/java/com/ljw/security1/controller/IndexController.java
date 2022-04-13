package com.ljw.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljw.security1.model.User;
import com.ljw.security1.repository.UserRepository;

@Controller  // 컨트롤러는  view 를 리턴
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 src/main/resources/
		//뷰리졸버 설정 : template(prefix), . mustache(suffix)  // 의존성등록하면 생략가능
		return "index";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	@GetMapping("/loginForm")  //securityConfig 파일 만들경우 들어가짐
	public String loginForm() {
		return "loginForm";
	}
	
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user); //회원가입,
		
		return "redirect:/loginForm";
	}
	
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료";
	}
	
	
	
	@Secured("ROLE_ADMIN")  //securityconfig 에서 어노테이션 활성화  
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
}
