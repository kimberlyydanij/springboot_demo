package com.cos.controllerdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpRedirectionController {
	
	@GetMapping("/home")
	public String home() {
		//1만줄 이 있다고 가정함
		
		return "home";
	}

	@GetMapping("/away")
	public String away() {
		//다른코드
		
		return "redirect:/home"; //리다이렉션이 된다 (@controller에서만 발동함)
		
	}
}
