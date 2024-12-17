package com.ip.pi_kurs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class SecurityController {
    @GetMapping(value = {"", "/"})
    public String authorization() {
        return "security/authorization";
    }
    @PostMapping("/login")
    public String login() {
        return "main/main";
    }
}
