package com.example.membership.controller;

import com.example.membership.dto.UserMemberships;
import com.example.membership.service.MembershipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000") // Next 요청 허용
public class MembershipController {

    private MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/api/userMembership/register")
    public void registerUserMembership(@RequestBody UserMemberships userMembershipDto) {
        membershipService.registerUserMembership(userMembershipDto);
    }
}
