package com.example.redisSession.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RedisController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/login")
    public String getSessionId(HttpSession session) {
        System.out.println(session.getId());
        session.setAttribute("name" , "name="+session.getId());
        System.out.println(session.getAttribute("name"));
        return session.getId();
    }

    @GetMapping("/name")
    public String getName(HttpSession session) {
        System.out.println(session.getId());
        String name = (String)session.getAttribute("name");
        System.out.println(name);
        return name;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "logout success";
    }
}
