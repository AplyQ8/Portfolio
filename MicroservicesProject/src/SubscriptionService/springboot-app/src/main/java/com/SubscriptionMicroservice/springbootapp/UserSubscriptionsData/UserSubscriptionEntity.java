package com.SubscriptionMicroservice.springbootapp.UserSubscriptionsData;

import com.SubscriptionMicroservice.springbootapp.SubscriptionData.SubscriptionEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_subscriptions")
@Data
public class UserSubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscription;

    @Column(name = "username")
    private String username;
}
