package com.UserManagementService.springbootapp.Entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_email")
    private String email;

    public UserEntity() {
    }

    public UserEntity(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
