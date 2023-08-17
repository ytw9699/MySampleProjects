package io.security.basicSecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity//웹보안 활성화 위해 추가
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
             .authorizeRequests()
             .anyRequest().authenticated();//어떤요청에도 인증을 요구

        http
                .formLogin()
                //.loginPage("/loginPage") // 로그인 페이지
                .defaultSuccessUrl("/") //로그인 성공시 이동
                //.defaultSuccessUrl("/",true) //https://www.inflearn.com/questions/193737/defaultsuccessurl-%EC%9E%91%EB%8F%99-%EC%88%9C%EC%84%9C
                .failureUrl("/login")//로그인 실패시 이동
                .usernameParameter("userId")
                .passwordParameter("passWd")
                .loginProcessingUrl("/login_proc")
                .successHandler(new AuthenticationSuccessHandler() { // 성공시 이 핸들러 호출
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("authentication = " + authentication.getName());  //인증에 성공한 유저 네임 ,    //authentication 은 로그인 성공시 인증한 결과를 담는다
                        response.sendRedirect("/"); //인증 성공뒤 루트페이지로 이동
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {  //실패했을경우 이 handler 호출
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("exception = " + exception.getMessage());
                        response.sendRedirect("/login"); //실패시 이동
                    }
                })
                .permitAll();//loginPage url 허용
    }
}
