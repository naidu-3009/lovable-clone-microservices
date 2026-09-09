package com.lovable_clone_microservices.common_library.security;

import com.lovable_clone_microservices.common_library.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Component
public class AuthUtil {


    @Value("${jwt.secret-key}")
    private  String jwtSecretKey;

    public String generateAccessToken(UserDto user){
        return Jwts.builder()
                .subject(user.username())
                .claim("userId",user.id().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*100))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

    }


//    Claims can have anything they are our custom blanks to fill anything
//             .subject(...)       → sub
//            .claim(...)         → custom payload field
//            .issuedAt(...)      → iat
//            .expiration(...)    → exp
//            .signWith(...)      → signature
//            .compact()          → final JWT string

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8)); //we are just creating the signing in key so header+payload+this key== signature
    }



    //header.payload.sign->jwt tokent header==what algorithm we have used,payload=our info(subject,claim)->we inserted this +issued at and expired at inpayload
    //remember jwt is not encrypted->its just base 64 encoded what makes special?->key(storedin server)+algo(from header)=sign
    //payload+secret key+ hs256(algo)(came from header)==signature

    public JwtUserPrincipal verifyAccessToken(String token){
        Claims claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
        Long userId = Long.parseLong(claims.get("userId", String.class));

        String userName=claims.getSubject();
        return new JwtUserPrincipal(userId.toString(),userName,null,new ArrayList<>());
    }





//  1. Jwts.parser() parses the received JWT.
//  2. .verifyWith(getSecretKey()) verifies the JWT's signature using your secret key. If the token was tampered with or is invalid, verification fails.
//  3. .parseSignedClaims(token).getPayload() extracts the JWT's payload as Claims.
//  4. From the claims, you extract the custom userId:
//     claims.get("userId", String.class)
//    and convert it to Long.
//  5. You extract the username from the standard subject (sub):
//            claims.getSubject()
//  6. Finally, you create a JwtUserPrincipal containing:
//            - userId
//   - username
//   - authorities (currently an empty list)
//    In short: JWT → verify signature → extract payload → get userId + username → create JwtUserPrincipal → give it to the authentication filter.


    public Long getCurrentUserId(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication ==null || !(authentication.getPrincipal() instanceof  JwtUserPrincipal)){
            throw  new AuthenticationCredentialsNotFoundException("No JWT Found");
        }
        return Long.parseLong(((JwtUserPrincipal) authentication.getPrincipal()).userId());

    }


}
