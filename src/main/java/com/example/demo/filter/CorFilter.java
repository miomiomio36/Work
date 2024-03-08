package com.example.demo.filter;



import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println("Origin: " + req.getHeader("Origin"));
        System.out.println("req.getRequestURL(): " + req.getRequestURL());

        req.setCharacterEncoding("UTF-8");
        res.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Methods","GET, POST, PATCH, DELETE");
        res.addHeader("Access-Control-Allow-Headers","token,Content-type");
        res.setContentType("application/json;charset=utf-8");

        String str = req.getRequestURI();
        System.out.println("req.getRequestURI(): " + str);
        System.out.println("我是CorFilter");
    }
}
