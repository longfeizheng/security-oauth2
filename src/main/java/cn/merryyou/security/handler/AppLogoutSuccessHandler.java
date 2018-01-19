package cn.merryyou.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created on 2018/1/19.
 *
 * @author zlf
 * @since 1.0
 */
@Slf4j
public class AppLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            removeAuthSession(authentication, sessionRegistry);
            httpServletRequest.getSession().invalidate();
            httpServletResponse.sendRedirect("login.html?logout");
        }
    }

    private void removeAuthSession(Authentication authentication, SessionRegistry sessionRegistry) {
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);
        if (sessions.size() > 0) { // there is only 1 session allowed
            log.debug("removing session {} from registry", sessions.get(0).getSessionId());
            sessionRegistry.removeSessionInformation(sessions.get(0).getSessionId());
        }
    }
}
