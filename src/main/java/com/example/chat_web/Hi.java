package com.example.chat_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hi {
    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }
}
