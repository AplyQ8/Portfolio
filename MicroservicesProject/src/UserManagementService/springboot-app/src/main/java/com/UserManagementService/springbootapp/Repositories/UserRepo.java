package com.UserManagementService.springbootapp.Repositories;

import com.UserManagementService.springbootapp.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface UserRepo  extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    UserEntity findByUsername(String username);

}
