package cn.merryyou.security;

import cn.merryyou.security.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@Slf4j
public class SecurityOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOauth2Application.class, args);
    }

    @GetMapping("/user")
    public Object getCurrentUser1(Authentication authentication) {
        log.info("【SecurityOauth2Application】 getCurrentUser1 authenticaiton={}", JsonUtil.toJson(authentication));
        return authentication;
    }
}
