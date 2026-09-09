package com.lovable_clone_microservices.account_service.entity;


import com.lovable_clone_microservices.common_library.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
    public class Subscription {


    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(nullable = false,name = "user_id")
    User user;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(nullable = false,name = "plan_id")
    Plan plan;

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    String stripeSubscriptionId;

    @Enumerated(EnumType.STRING)
    SubscriptionStatus status;

    Instant currentPeriodStart;
    Instant currentPeriodEnd;

    @CreationTimestamp
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;
    Boolean cancelAtPeriodEnd=false;

}
