package com.SubscriptionMicroservice.springbootapp.SubscriptionData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriptionRepo extends JpaRepository<SubscriptionEntity, Long> {
    @Query(value = "SELECT s.type FROM subscriptions WHERE s.id = :id", nativeQuery = true)
    String getTypeById(@Param("id") Long id);
}
