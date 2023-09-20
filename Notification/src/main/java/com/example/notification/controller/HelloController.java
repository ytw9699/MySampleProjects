package com.example.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("hello", "hello");
        return "index.html";
    }
}
