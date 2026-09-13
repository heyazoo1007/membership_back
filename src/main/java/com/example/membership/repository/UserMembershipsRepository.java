package com.example.membership.repository;

import com.example.membership.dto.UserMemberships;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMembershipsRepository extends JpaRepository<UserMemberships, Long>  {

    Optional<UserMemberships> getUserMembershipsByUserIdAndMembershipId(long userId, long membershipId);

}
