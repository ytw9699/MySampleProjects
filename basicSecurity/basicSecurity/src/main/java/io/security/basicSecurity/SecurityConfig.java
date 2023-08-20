package io.security.basicSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity//웹보안 활성화 위해 추가
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
             .authorizeRequests()
             .anyRequest().authenticated();//어떤요청에도 인증을 요구

        http
                .formLogin()//이렇게 api 부를때 UsernamePasswordAuthenticationFilter이 생성된다
                //.loginPage("/loginPage") // 로그인 페이지, 기본은 login
                .defaultSuccessUrl("/") //로그인 성공시 이동
                //.defaultSuccessUrl("/",true) //https://www.inflearn.com/questions/193737/defaultsuccessurl-%EC%9E%91%EB%8F%99-%EC%88%9C%EC%84%9C
                .failureUrl("/login")//로그인 실패시 이동
                .usernameParameter("userId")
                .passwordParameter("passWd")
                .loginProcessingUrl("/login_proc")//기본은 login
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
                        response.sendRedirect("/login"); //실패시 로그인 페이지 이동
                    }
                })
                .permitAll();//loginPage url은 인증 없이 허용


        http
                .logout()
                .logoutUrl("/logout")  //로그아웃은 기본적을 post 방식으로 처리한다, 기본 디폴트 /logout
                .logoutSuccessUrl("login")
                .addLogoutHandler(new LogoutHandler() { //기존에 로그아웃시 핸들러가 동작하면서 각종처리를 하는데 거기다 플러스 커스텀으로 add할것을 추가한다
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        //세션 무효화 처리는 이미 다른 핸들러에서 하고있지만 한번 더 추가해봄
                        HttpSession session = request.getSession();
                        session.invalidate();
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {   //logoutSuccessUrl은 그냥 이동만 하고 이 핸들러는 좀더 다양한 로직 구현 가능
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/login");
                    }
                })
                .deleteCookies("remember-me");    //서버에서 만든 쿠키 삭제

        http
                .rememberMe()
                .rememberMeParameter("remember")//기본 디폴트 remember-me
                .tokenValiditySeconds(3600) //기본은 14일이지만 만료시간 1시간 설정
                .userDetailsService(userDetailsService); //유저 계정 조회
    }
}
