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

	public static void main(String[] args) {
		SpringApplication.run(SecurityOauth2Application.class, args);
	}
}
