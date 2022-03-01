package com.example.springboottdd.repository;

import com.example.springboottdd.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 이승환
 * @since 2022-03-01
 */
public interface MembershipRepository extends JpaRepository<Membership, Long> {



}
