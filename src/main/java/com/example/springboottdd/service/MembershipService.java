package com.example.springboottdd.service;

import com.example.springboottdd.domain.Membership;
import com.example.springboottdd.enums.MembershipErrorResult;
import com.example.springboottdd.enums.MembershipType;
import com.example.springboottdd.exception.MembershipException;
import com.example.springboottdd.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 이승환
 * @since 2022-03-03
 */
@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public Membership addMembership(final String userId, final MembershipType membershipType, final Integer point) {
        final Membership membership = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);

        if (membership != null)  // 중복된 회원이 있을경우 Exception 호출
            throw new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);

        return null;
    }
}
