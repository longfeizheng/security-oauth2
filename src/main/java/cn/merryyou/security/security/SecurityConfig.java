//package cn.merryyou.security.security;
//
//import cn.merryyou.security.handler.AppLogoutSuccessHandler;
//import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.web.session.HttpSessionEventPublisher;
//
///**
// * Created on 2018/1/19.
// *
// * @author zlf
// * @since 1.0
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                .and()
//                .authorizeRequests().antMatchers("/session/invalid")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement()
//                .invalidSessionUrl("/session/invalid")
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
////                .expiredSessionStrategy(new MyExpiredSessionStrategy())
//                .sessionRegistry(sessionRegistry())
//                .and()
//                .and()
//                .logout()
//                .permitAll().logoutSuccessHandler(appLogoutSuccessHandler())
//                .and()
//                .csrf().disable();
//    }
//
//    @Bean
//    public SessionRegistry sessionRegistry() {
//        SessionRegistry sessionRegistry = new SessionRegistryImpl();
//        return sessionRegistry;
//    }
//
//    @Bean
//    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
//    }
//
//    @Bean
//    protected LogoutSuccessHandler appLogoutSuccessHandler() {
//        return new AppLogoutSuccessHandler();
//    }
//}
