package com.UserManagementService.springbootapp.Repositories;

import com.UserManagementService.springbootapp.Entities.LoggedInUserEntity;
import com.UserManagementService.springbootapp.Entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggedUserRepo extends JpaRepository<LoggedInUserEntity, Long> {
    boolean existsByUsername(String username);
    @Transactional
    void deleteByUsername(String username);
}
