package com.SubscriptionMicroservice.springbootapp.UserSubscriptionsData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSubscriptionsRepo extends JpaRepository<UserSubscriptionEntity, Long> {
    @Query(value = "SELECT CASE WHEN COUNT(us) > 0 THEN true ELSE false END FROM UserSubscriptionEntity us WHERE us.username = :username AND us.subscription.id = :subscriptionId")
    boolean existsByUsernameAndSubscription_Id(@Param("username") String username, @Param("subscriptionId") Long subscriptionId);

    @Query(value = "SELECT CASE WHEN COUNT(us) > 0 THEN true ELSE false END FROM UserSubscriptionEntity us WHERE us.username = :username AND us.subscription.name = :subscriptionName")
    boolean existsByUsernameAndSubscriptionName(@Param("username") String username, @Param("subscriptionName") String subscriptionName);
}
