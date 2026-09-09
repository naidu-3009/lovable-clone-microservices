package com.lovable_clone_microservices.account_service.services;


import com.lovable_clone_microservices.account_service.dto.auth.AuthResponse;
import com.lovable_clone_microservices.account_service.dto.auth.LoginRequest;
import com.lovable_clone_microservices.account_service.dto.auth.SignUpRequest;

public interface AuthService {
    AuthResponse signup(SignUpRequest request);

    AuthResponse login(LoginRequest request);
}
