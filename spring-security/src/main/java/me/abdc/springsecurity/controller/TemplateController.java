package me.abdc.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/login")
    public String loginView() {
        return "/login";
    }

    @GetMapping("/course")
    public String courseView() {
        return "/course";
    }
}
