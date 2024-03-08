package com.example.demo.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.Default.DefaultConst;
import com.example.demo.enums.CodeEnum;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.SpringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MyFilter implements Filter {
    @Resource
    JwtUtils jwtUtils;

    private Map<String,String> ROLE_MAP  =Map.of("admin", DefaultConst.ROLE_ADMIN,"teacher",DefaultConst.ROLE_TEACHER,"student",DefaultConst.ROLE_STUDENT);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(jwtUtils == null){
            jwtUtils = (JwtUtils) SpringUtils.getBean("JwtUtils");
        }
        String URI = req.getRequestURI();
        System.out.println(URI);
        String[] path = URI.split("/");
        //获取请求头中的token
        String token = req.getHeader("token");
        if(token == null||token.length()==0){
            res.getWriter().write(CodeEnum.TOKEN_NULL.toString());
        }
        else{
            DecodedJWT jwt =  jwtUtils.resolveJwt(token);
            String role = jwtUtils.toRole(jwt);
            String verifyRole = ROLE_MAP.get(path[0]);
            if(Objects.equals(verifyRole,role)){
                filterChain.doFilter(req,res);
            }
            else {
                res.getWriter().write(CodeEnum.ROLE_ERROR.toString());
            }
        }

    }
}
