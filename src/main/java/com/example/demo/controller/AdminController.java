package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }
}
