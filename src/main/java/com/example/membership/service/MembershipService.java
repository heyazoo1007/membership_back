package com.example.membership.service;

import com.example.membership.common.exception.BaseException;
import com.example.membership.dto.Memberships;
import com.example.membership.dto.UserMemberships;
import com.example.membership.dto.Users;
import com.example.membership.repository.MembershipRepository;
import com.example.membership.repository.UserMembershipsRepository;
import com.example.membership.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
public class MembershipService {
    private final UserMembershipsRepository userMembershipsRepository;
    private MembershipRepository membershipRepository;
    private UserRepository userRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository,
                             UserMembershipsRepository userMembershipsRepository,
                             UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.userMembershipsRepository = userMembershipsRepository;
        this.userRepository = userRepository;
    }

    public void registerUserMembership(UserMemberships userMembershipDto) {

        // 1. 멤버십 등록된 사용자인지 확인(중복 등록 방지)
        Optional<UserMemberships> userMembership = userMembershipsRepository.getUserMembershipsByUserIdAndMembershipId(userMembershipDto.getUserId(), userMembershipDto.getMembershipId());
        if (!userMembership.isEmpty()) {
            // avoid duplicate join
            throw BaseException.DUPLICATE_USER_MEMBERSHIP; // You've already joined this membership.
        }

        // 2. 멤버십 인원 마감전인지
        Memberships membership = membershipRepository.getMembershipsByMembershipId(userMembershipDto.getMembershipId()).orElseThrow(
                () -> BaseException.MEMBERSHIP_NOT_FOUND);

        if (membership.getTotalLimit() < membership.getCurrentCount()) {
            throw BaseException.OUT_OF_MEMBERSHIP_OCCUPATION; // membership is out of occupation.
        }

        // 3. 유저-멤버십 추가
        UserMemberships userMembershipVO = new UserMemberships();
        userMembershipVO.setUserId(userMembershipDto.getUserId());
        userMembershipVO.setMembershipId(userMembershipDto.getMembershipId());
        userMembershipVO.setRegisteredAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeUtils.FORMAT_STRING_TIMESTAMP)));
        userMembershipsRepository.save(userMembershipVO);

        // 4. user의 membership_id update
        Users user = userRepository.getUsersByUserId(userMembershipDto.getUserId()).orElseThrow(() -> BaseException.USER_NOT_FOUND);
        user.setMembershipId(userMembershipDto.getMembershipId());
        user.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeUtils.FORMAT_STRING_TIMESTAMP)));
        userRepository.save(user); // 스냅샷으로 변경사항 확인 후 update? - dirty checking

        // 5. member의 count + 1
        membership.setCurrentCount(membership.getCurrentCount() + 1);
        membershipRepository.save(membership);
    }
}
