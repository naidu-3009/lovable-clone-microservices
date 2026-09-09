package com.lovable_clone_microservices.common_library.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class   JwtAuthFilter extends OncePerRequestFilter {

    private  final AuthUtil authUtil;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {
            log.info("incoming request {}",request.getRequestURI());
            SecurityContextHolder securityContextHolder;
            final String requestHeaderToken=request.getHeader("Authorization");

            if(requestHeaderToken==null || !(requestHeaderToken.startsWith("Bearer"))){
                filterChain.doFilter(request,response);
                return;
            }
            String token=requestHeaderToken.split("Bearer ")[1];//THe total header "Bearer header.payload.sign is split into 2 parts and here we are taking the 2nd part"
            JwtUserPrincipal userPrincipal=authUtil.verifyAccessToken(token);

            if(userPrincipal!=null && SecurityContextHolder.getContext().getAuthentication() ==null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userPrincipal,null, userPrincipal.authorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(request,response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }


//        1. Read the Authorization header from the incoming request.
//        2. If there is no Bearer <JWT> token, continue the filter chain without authenticating the user.
//        3. If a Bearer token exists, extract the JWT from the header.
//        4. Pass the JWT to AuthUtil.verifyAccessToken() to verify it and extract the user's information as a JwtUserPrincipal.
//        5. Create a UsernamePasswordAuthenticationToken using that principal.
//        6. Put that Authentication object into the SecurityContextHolder using setAuthentication(). This tells Spring Security who the current authenticated user is.
//        7. Call filterChain.doFilter() so the request continues through the remaining filters and eventually reaches the controller.
//        8. If anything goes wrong, the exception is passed to the HandlerExceptionResolver.
//        In short: Request → JWT → verify → JwtUserPrincipal → Authentication → SecurityContextHolder → continue request → Controller.

    }
}
