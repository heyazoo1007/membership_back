package com.example.membership.service;

import com.example.membership.dto.Memberships;
import com.example.membership.dto.UserMemberships;
import com.example.membership.dto.Users;
import com.example.membership.repository.MembershipRepository;
import com.example.membership.repository.UserMembershipsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MembershipService {
    private final UserMembershipsRepository userMembershipsRepository;
    private MembershipRepository membershipRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository,
                             UserMembershipsRepository userMembershipsRepository) {
        this.membershipRepository = membershipRepository;
        this.userMembershipsRepository = userMembershipsRepository;
    }

    public void registerUser(UserMemberships userMembershipDto) {

        // 1. 멤버십 등록된 사용자인지 확인(중복 등록 방지)
        Optional<UserMemberships> userMembership = userMembershipsRepository.getUserMembershipsByUserIdAndMembershipId(userMembershipDto.getUserId(), userMembershipDto.getMembershipId());

        if (!userMembership.isEmpty()) {
            // you registered this membership already.
            return;
        }

        // 2. 멤버십 인원 마감전인지
        log.debug("test = " + userMembershipDto.getMembershipId());
        Optional<Memberships> membership = membershipRepository.getMembershipsByMembershipId(userMembershipDto.getMembershipId());
        log.debug("test = " + userMembershipDto.getMembershipId());
        if (membership.isPresent()) {
            if (membership.get().getTotalLimit() < membership.get().getCurrentCount()) {
                // membership is out of occupation.
                return;
            }
        }

        // 3. 멤버십 증가
        UserMemberships userMembershipVO = new UserMemberships();
        userMembershipVO.setUserId(userMembershipDto.getUserId());
        userMembershipVO.setMembershipId(userMembershipDto.getMembershipId());
        userMembershipsRepository.save(userMembershipVO);
    }
}
