package com.example.springboottdd.repository;

import com.example.springboottdd.domain.Membership;
import com.example.springboottdd.enums.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 이승환
 * @since 2022-03-01
 */
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findByUserIdAndMembershipType(final String userId, final MembershipType membershipType);

    List<Membership> findAllByUserId(final String userId);
}
