package com.lovable_clone_microservices.account_service.dto.auth;

public record AuthResponse(String token,UserProfileResponse user) {
}
