package com.nagel.kuehne.ewallet.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/access")
public class MainController {

    @GetMapping("/all")
    public String allAccess(){
        return "Public Content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String helloUser(){
        return "User Content";
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin(){
        return "Admin Content";
    }


}
