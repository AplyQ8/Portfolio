package com.SubscriptionMicroservice.springbootapp.Controllers;


import com.SubscriptionMicroservice.springbootapp.Requests.UserHasSubscriptionRequest;
import com.SubscriptionMicroservice.springbootapp.Services.SubscriptionService;
import com.SubscriptionMicroservice.springbootapp.UserSubscriptionsData.UserSubscriptionsRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userSubscription")
public class SubscriptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    UserSubscriptionsRepo userSubscriptionsRepo;

    @Autowired
    public SubscriptionController(UserSubscriptionsRepo userSubscriptionsRepo) {
        this.userSubscriptionsRepo = userSubscriptionsRepo;
    }

//    @PostMapping("/{userSubscriptionInfo}")
//    public boolean findUserWithSubscription(@RequestBody UserHasSubscriptionRequest userHasSubscriptionRequest){
//        return userSubscriptionsRepo.existsByUsernameAndSubscriptionName(
//                userHasSubscriptionRequest.username,
//                userHasSubscriptionRequest.subscriptionName
//        );
//    }
    @PostMapping("/{userSubscriptionInfo}")
    public ResponseEntity<?> findUserWithSubscription(@PathVariable String userSubscriptionInfo, @RequestBody UserHasSubscriptionRequest userHasSubscriptionRequest){
        boolean userHasSubscription = userSubscriptionsRepo.existsByUsernameAndSubscriptionName(
                userHasSubscriptionRequest.username,
                userHasSubscriptionRequest.subscriptionName
        );
        if(userHasSubscription){
            return ResponseEntity.ok(true);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(userHasSubscriptionRequest.username + " does not have the subscription with name: " + userHasSubscriptionRequest.subscriptionName);
        }
    }
}
