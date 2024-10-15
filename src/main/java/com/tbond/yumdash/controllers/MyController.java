package com.tbond.yumdash.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @GetMapping("/hello-world")
    @ResponseBody
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/about-me")
    public String aboutMe() {
        return "aboutme";
    }
}
