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
import org.apache.tomcat.util.json.Token;

import java.io.IOException;
import java.util.Arrays;
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
        System.out.println("我是MyFilter");
        String header2 = req.getHeader("User-Agent");
        if (header2 == null || header2 == "") {
            System.out.println("请求头没来");
        }
        if(jwtUtils == null){
            jwtUtils = (JwtUtils) SpringUtils.getBean("JwtUtils");
        }
        String URI = req.getRequestURI();
        System.out.println(URI);
        String[] path = URI.split("/");
        System.out.println("path:"+ Arrays.toString(path));
        //获取请求头中的token
        String token = req.getHeader("token");
        System.out.println(req);
        System.out.println("传进来的Token:"+token);
        if(token == null||token.length()==0){
            System.out.println("token不存在");
            res.getWriter().write(CodeEnum.TOKEN_NULL.toString());
        }
        else{
            DecodedJWT jwt =  jwtUtils.resolveJwt(token);
            if(jwt==null){
                System.out.println("token不合法");
                res.getWriter().write(CodeEnum.TOKEN_ILLEGAL.toString());
            }
            else{
                String role = jwtUtils.toRole(jwt);
                System.out.println("path[1]:"+path[1]);
                String verifyRole = ROLE_MAP.get(path[1]);
                System.out.println("需要的角色权限:"+verifyRole);
                System.out.println("token的角色权限:"+role);
                if(Objects.equals(verifyRole,role)){
                    filterChain.doFilter(req,res);
                }
                else {
                    res.getWriter().write(CodeEnum.ROLE_ERROR.toString());
                }
            }
        }
    }
}
