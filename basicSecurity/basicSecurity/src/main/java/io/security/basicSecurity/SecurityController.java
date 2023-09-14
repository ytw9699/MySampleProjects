package io.security.basicSecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("loginPage")
    public String loginPage(){
        return "loginPage";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/admin/pay")
    public String adminPay(){
        return "adminPay";
    }

    @GetMapping("/admin/**")
    public String admin(){
        return "admin";
    }

    @GetMapping("/admin2")
    public String admin2(){
        return "admin2";
    }

    @GetMapping("/every")
    public String every(){
        return "every";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/denied")
    public String denied(){
        return "denied";
    }

    @GetMapping("/context")
    public String context(HttpSession session){

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

        SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        Authentication authentication2 = context.getAuthentication();

        return "context";
    }

    @GetMapping("/thread")
    public String thread(){

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication1);

        new Thread(//자식쓰레드 인데 기본 mode_threadlocal 설정으로는 authentication객체가 공유안됨 그래서 MODE_INHERITABLETHREADLOCAL로 바꿔주면 공유됨
                new Runnable() {
                    @Override
                    public void run() {
                        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
                        System.out.println(authentication2);
                    }
                }
        ).start();

        return "thread";
    }
}
