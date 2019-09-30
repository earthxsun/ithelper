package com.example.ithelper.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtil {

    // 过期时间5分钟
    public static final long EXPIRE_TIME = 8 * 60 * 60 * 1000;

    public static boolean verify(String token,String username,String secret){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username",username)
                    .build();
            verifier.verify(token);
            System.out.println("token 有效");
            return true;
        } catch (Exception e) {
            System.out.println("token 无效效");
            e.printStackTrace();
            return false;
        }
    }

    public static String getUsername(String token){
        try{
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String sign(String username, String secret){
        try{
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("username",username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
