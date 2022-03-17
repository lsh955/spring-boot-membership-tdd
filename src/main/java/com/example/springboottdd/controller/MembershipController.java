package com.example.springboottdd.controller;

import com.example.springboottdd.dto.MembershipAddResponse;
import com.example.springboottdd.dto.MembershipDetailResponse;
import com.example.springboottdd.dto.MembershipRequest;
import com.example.springboottdd.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<MembershipAddResponse> addMembership(
            @RequestHeader(USER_ID_HEADER) final String userid,
            @RequestBody @Valid final MembershipRequest membershipRequest
    ) {
        final MembershipAddResponse membershipAddResponse = membershipService.addMembership(
                userid,
                membershipRequest.getMembershipType(),
                membershipRequest.getPoint()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membershipAddResponse);
    }

    @GetMapping("/api/v1/membership/list")
    public ResponseEntity<List<MembershipDetailResponse>> getMembershipList(
            @RequestHeader(USER_ID_HEADER) final String userId
    ) {

        return ResponseEntity.ok(membershipService.getMembershipList(userId));
    }

    @GetMapping("/api/v1/memberships/{id}")
    public ResponseEntity<MembershipDetailResponse> getMembership(
            @RequestHeader(USER_ID_HEADER) final String userId,
            @PathVariable final Long id
    ) {

        return ResponseEntity.ok(membershipService.getMembership(id, userId));
    }

    @DeleteMapping("/api/v1/memberships/{id}")
    public ResponseEntity<Void> removeMembership(
            @RequestHeader(USER_ID_HEADER) final String userId,
            @PathVariable final Long id
    ) {
        membershipService.removeMembership(id, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/v1/memberships/{id}/accumulate")
    public ResponseEntity<Void> accumulateMembershipPoint(
            @RequestHeader(USER_ID_HEADER) final String userId,
            @PathVariable final Long id,
            @RequestBody @Valid final MembershipRequest membershipRequest
    ) {
        membershipService.accumulateMembershipPoint(id, userId, membershipRequest.getPoint());

        return ResponseEntity.noContent().build();
    }
}
