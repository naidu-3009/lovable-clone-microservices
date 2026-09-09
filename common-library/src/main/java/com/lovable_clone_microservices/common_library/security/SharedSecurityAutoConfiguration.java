package com.lovable_clone_microservices.common_library.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class SharedSecurityAutoConfiguration {

    @Bean
    public AuthUtil authUtil(){
        return new AuthUtil();
    }

    public JwtAuthFilter jwtAuthFilter(AuthUtil authUtil, HandlerExceptionResolver handlerExceptionResolver){
        return new JwtAuthFilter(authUtil,handlerExceptionResolver);
    }


}
