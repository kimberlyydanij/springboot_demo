package com.cos.controllerdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //파일을 return 할 것이기 때문!!
public class HttpRespController {
	
	@GetMapping("/txt")
	public String txt() {
		
		return "a.txt"; //프레임워크를 사용(틀이 이미 정해져 있음) - 일반 정적파일들은 resources/static 폴더 내부에 두면 디폴트 경로이다.
	}
	
	@GetMapping("/mus")
	public String mus() {
		return "b"; //머스태치 템플릿 엔진 라이브러리 등록완료 - template 폴더안에 .mustache를 두면 확장자 없이 파일명만 적으면 자동으로 찾아온다.
		                     //확장자를 적으면 static 폴더를 찾아가고 없으면 template를 찾아간다.
	}

	@GetMapping("/jsp")
	public String jsp() {
		return "c"; //jsp 엔진사용 : src/main/webapp 폴더가 디폴트 경로
		                     // prefix /WEB-INF/views/ suffix: .jsp (viewResolver  가 발동시킴)
	}
}
