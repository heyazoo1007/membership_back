package com.example.membership.repository;

import com.example.membership.dto.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> getUsersByUserId(long userId);
}
