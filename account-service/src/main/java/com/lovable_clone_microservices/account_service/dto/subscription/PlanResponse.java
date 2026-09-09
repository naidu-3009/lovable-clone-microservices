package com.lovable_clone_microservices.account_service.dto.subscription;

public record PlanResponse(    Long id,
        String name,
        String stripePriceId,
        Integer maxProjects,
        Integer maxTokensPerDay,
        Integer maxPreviews,
        Boolean unlimitedAi,
        Boolean active) {
}
