package com.example.springboottdd.repository;

import com.example.springboottdd.domain.Membership;
import com.example.springboottdd.enums.MemebershipType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 이승환
 * @since 2022-03-01
 */
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findByUserIdAndMembershipType(final String userId, final MemebershipType memebershipType);

}
