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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 이승환
 * @since 2022-03-03
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기전용
public class MembershipService {

    /**
     * ratePointService 변수 이름에 대한 고찰
     * <p>
     * pointService로 해주면
     * 추후에 FixPointService가 추가되었을 때
     * PointService 타입의 빈이 2개이고,
     * pointService라는 이름의 빈은 존재하지 않으므로 에러가 발생하게 된다.
     * 그러므로 미래에 이러한 문제를 방지하기 위해 변수의 이름을 ratePointService로 해준 것.
     */
    private final RatePointService ratePointService;
    private final MembershipRepository membershipRepository;

    /**
     * 멤버십 등록
     *
     * @param userId
     * @param membershipType
     * @param point
     * @return
     */
    @Transactional
    public MembershipAddResponse addMembership(final String userId,
                                               final MembershipType membershipType,
                                               final Integer point
    ) {
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

    /**
     * 멤버십목록 조회
     *
     * @param userId
     * @return
     */
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

    /**
     * 멤버십상세 조회
     *
     * @param membershipId
     * @param userId
     * @return
     */
    public MembershipDetailResponse getMembership(final Long membershipId,
                                                  final String userId
    ) {
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

    /**
     * 멤버십 삭제
     *
     * @param membershipId
     * @param userId
     */
    @Transactional
    public void removeMembership(final Long membershipId,
                                 final String userId
    ) {
        final Optional<Membership> optionalMembership = membershipRepository.findById(membershipId);
        final Membership membership = optionalMembership.orElseThrow(() ->
                new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND)
        );

        if (!membership.getUserId().equals(userId))
            throw new MembershipException(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);

        membershipRepository.deleteById(membershipId);
    }

    /**
     * 멤버십포인트 적립
     *
     * @param membershipId
     * @param userId
     * @param amount
     */
    @Transactional
    public void accumulateMembershipPoint(final Long membershipId,
                                          final String userId,
                                          final int amount
    ) {
        final Optional<Membership> optionalMembership = membershipRepository.findById(membershipId);
        final Membership membership = optionalMembership.orElseThrow(() ->
                new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND)
        );

        if (!membership.getUserId().equals(userId))
            throw new MembershipException(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);

        final int additionalAmount = ratePointService.calculateAmount(amount);

        membership.setPoint(additionalAmount + membership.getPoint());
    }
}
