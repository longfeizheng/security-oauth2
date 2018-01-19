package cn.merryyou.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SecurityOauth2Application {

	@GetMapping("/user")
	public Object getCurrentUser1(Authentication authentication) {
		return authentication;
	}
	@GetMapping("/session/invalid")
	public String invalidSession(){
		return "session失效";
	}

	@GetMapping("/")
	public String index(){
		return "主页";
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityOauth2Application.class, args);
	}
}
