package com.lovable_clone_microservices.account_service.dto.subscription;

public record UsageTodayResponse(
        Integer tokensUsed, Integer tokensLimit,Integer previewsRunning,Integer previewsLimit
        ) {
}
