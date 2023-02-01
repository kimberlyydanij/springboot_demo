package com.cos.controllerdemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.controllerdemo.domain.User;

@RestController
public class HttpResponseJsonController {
	
	@GetMapping("/resp/json")
	public String respJson() {
		return "{\"username\":\"aa\"}";
	}
	
	@GetMapping("/resp/json/javaobject")
	public User respJsonObject() {
		
		User user = new User();
		user.setUsername("홍길동");
		return user; //MessageConverter가 자동으로 JavaObject를 Json(구 :XML)으로 변경해서 통신을 통해 응답을 줌
		                         //@RestController 일대만 MessageConverter가 작동한다.
	}

}
