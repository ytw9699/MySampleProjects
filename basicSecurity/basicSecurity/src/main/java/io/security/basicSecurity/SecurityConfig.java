package io.security.basicSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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

        http.sessionManagement() //동시 세션 제어
            //.invalidSessionUrl("/invalid")//세션이 유효하지 않을때 이동할 페이지 , 만약 expiredUrl과 둘다 설정시에는 이설정이 우선순위 높음
            .maximumSessions(1) //세션 개수 1개로 제한 -1은 무한대
            .maxSessionsPreventsLogin(true);//최대 세션 개수 초과될때의 경우이다 // 디폴트 false이면 기존 세션이 만료된다. true 주면 로그인 자체를 더 못한다.
            //.expiredUrl("/expired") // 세션이 만료된 경우 이동할 페이지

        http.sessionManagement()//세션 고정 보호 및 세션정책
            .sessionFixation().changeSessionId()//인증했을때마다 세션 id가 바뀐다. 세션 고정 보호! 디폴트 changeSessionId인데 none 주면 안바뀌어 공격당함
                //migrateSession = 새로운 세션도 생성되는데 서블릿 3.1이하에서 작동하도록 기본값이며 그 이전의 세션에서 설정한 값들을 새로 설정그대로 사용 changeSessionId로 마찬가지이다
                //newSession = 세션 새롭게 생성되지만, 그 이전의 세션에서 설정한 값들을 새로 설정해야함
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
            //디폴트 IF_REQUIRED =필요시 생성, Always = 항상세션 생성, Never = 생성안하고 이미 존재시 사용, stateless=생성하지 않고, 존재해도 사용안함 JWT 인증시의 경우!

    }
}
