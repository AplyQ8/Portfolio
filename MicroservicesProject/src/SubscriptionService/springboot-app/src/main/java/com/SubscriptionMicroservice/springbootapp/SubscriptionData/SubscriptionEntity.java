package com.SubscriptionMicroservice.springbootapp.SubscriptionData;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subscriptions")
public class SubscriptionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description")
    private String description;


}
