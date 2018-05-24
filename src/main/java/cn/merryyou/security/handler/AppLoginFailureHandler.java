package cn.merryyou.security.handler;

import cn.merryyou.security.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2018/5/24.
 *
 * @author zlf
 * @since 1.0
 */
@Slf4j
@Component("appLoginFailureHandler")
public class AppLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = "";
        response.setContentType("application/json;charset=UTF-8");
        if(exception instanceof BadCredentialsException){
            message = "用户名或密码错误";
            response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.error(-1, message)));
        }
    }
}
