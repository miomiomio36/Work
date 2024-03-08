package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Component("JwtUtils")
public class JwtUtils {
    //密钥
    @Value("${spring.security.jwt.key}")
    private String key;
    //过期时间
    @Value("7")
    private int expire;

    //创建token的方法
    public String createJwt(String username,String role){
        Algorithm algorithm = Algorithm.HMAC256(key);//加密算法
        Date expire = this.expireTime();
        return JWT.create()
                .withClaim("name",username)
                .withClaim("role",role)
                .withExpiresAt(expire)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }
    //用于过期时间的算法
    private Date expireTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,expire*24);
        return calendar.getTime();
    }
    //校验token的方法
    public DecodedJWT resolveJwt(String headerToken){
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            try{
                DecodedJWT verify = jwtVerifier.verify(headerToken);//判断jwt是否合法是否被修改过
                //判断token是否过期
                Date expiresAt = verify.getExpiresAt();
                return new Date().after(expiresAt)?null:verify;
            }
            catch (JWTVerificationException e){
                e.printStackTrace();
                return null;
            }
    }

    //只从token中解析出角色的方法
    public String toRole(DecodedJWT jwt){
        Map<String, Claim> claimMap = jwt.getClaims();
        return claimMap.get("role").asString();
    }
}
