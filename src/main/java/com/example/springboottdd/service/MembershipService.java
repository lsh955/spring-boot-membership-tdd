package com.example.springboottdd.service;

import com.example.springboottdd.domain.Membership;
import com.example.springboottdd.dto.MembershipAddResponse;
import com.example.springboottdd.dto.MembershipDetailResponse;
import com.example.springboottdd.enums.MembershipErrorResult;
import com.example.springboottdd.enums.MembershipType;
import com.example.springboottdd.exception.MembershipException;
import com.example.springboottdd.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 이승환
 * @since 2022-03-03
 */
@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipAddResponse addMembership(final String userId, final MembershipType membershipType, final Integer point) {
        final Membership result = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);

        if (result != null)  // 중복된 회원이 있을경우 Exception 호출
            throw new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);

        final Membership membership = Membership.builder()
                .userId(userId)
                .point(point)
                .membershipType(membershipType)
                .build();

        final Membership savedMembership = membershipRepository.save(membership);

        return MembershipAddResponse.builder()
                .id(savedMembership.getId())
                .membershipType(savedMembership.getMembershipType())
                .build();
    }

    public List<MembershipDetailResponse> getMembershipList(final String userId) {
        final List<Membership> membershipList = membershipRepository.findAllByUserId(userId);

        return membershipList.stream()
                .map(v -> MembershipDetailResponse.builder()
                        .id(v.getId())
                        .membershipType(v.getMembershipType())
                        .point(v.getPoint())
                        .createdAt(v.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public MembershipDetailResponse getMembership(final Long membershipId, final String userId) {
        final Optional<Membership> optionalMembership = membershipRepository.findById(membershipId);
        final Membership membership = optionalMembership.orElseThrow(() ->
                new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND)
        );

        if (!membership.getUserId().equals(userId))
            throw new MembershipException(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);

        return MembershipDetailResponse.builder()
                .id(membership.getId())
                .membershipType(membership.getMembershipType())
                .point(membership.getPoint())
                .createdAt(membership.getCreatedAt())
                .build();
    }

    public void removeMembership(final Long membershipId, final String userId) {
        final Optional<Membership> optionalMembership = membershipRepository.findById(membershipId);
        final Membership membership = optionalMembership.orElseThrow(() ->
                new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND)
        );

        if (!membership.getUserId().equals(userId))
            throw new MembershipException(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);

        membershipRepository.deleteById(membershipId);
    }
}
