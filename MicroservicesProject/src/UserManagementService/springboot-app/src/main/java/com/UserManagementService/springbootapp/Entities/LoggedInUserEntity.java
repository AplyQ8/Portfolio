package com.UserManagementService.springbootapp.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "loggedIn_users")
public class LoggedInUserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    public LoggedInUserEntity(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public LoggedInUserEntity() {
    }

    public LoggedInUserEntity(String username) {
        this.username = username;
    }
}
