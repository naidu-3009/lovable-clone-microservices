package com.lovable_clone_microservices.account_service.dto.subscription;

public record PlanLimitResponse(
        String planName,
        Integer maxTokensPerPolicy,
        Integer maxProjects,
        boolean unlimitedAi
) {
}
