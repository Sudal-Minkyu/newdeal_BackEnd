package com.broadwave.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "loginForm";
    }

}
