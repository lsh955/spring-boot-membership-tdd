package com.example.springboottdd.controller;

import com.example.springboottdd.dto.MembershipRequest;
import com.example.springboottdd.dto.MembershipResponse;
import com.example.springboottdd.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.springboottdd.constants.MembershipConstants.USER_ID_HEADER;

/**
 * @author 이승환
 * @since 2022-03-04
 */
@RestController
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping("/api/v1/membership")
    public ResponseEntity<MembershipResponse> addMembership (
            @RequestHeader(USER_ID_HEADER) final String userid,
            @RequestBody @Valid final MembershipRequest membershipRequest
    ) {
        final MembershipResponse membershipResponse = membershipService.addMembership(
                userid,
                membershipRequest.getMembershipType(),
                membershipRequest.getPoint()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membershipResponse);
    }
}
