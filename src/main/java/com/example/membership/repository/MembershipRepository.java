package com.example.membership.repository;

import com.example.membership.dto.Memberships;
import com.example.membership.dto.UserMemberships;
import com.example.membership.dto.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Memberships, Long> {

    Optional<Memberships> getMembershipsByMembershipId(long id);

}
