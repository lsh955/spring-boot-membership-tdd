package com.example.springboottdd.repository;

import com.example.springboottdd.domain.Membership;
import com.example.springboottdd.enums.MemebershipType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author 이승환
 * @since 2022-03-01
 */
@DataJpaTest
public class MembershipRepositoryTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    @DisplayName("멤버십등록")
    public void MembershipSave() {
        // given
        final Membership membership = Membership.builder()
                .userId("userId")
                .membershipType(MemebershipType.NAVER)
                .point(10000)
                .build();

        // when
        final Membership result = membershipRepository.save(membership);

        // then
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getUserId()).isEqualTo("userId");
        Assertions.assertThat(result.getMembershipType()).isEqualTo(MemebershipType.NAVER);
        Assertions.assertThat(result.getPoint()).isEqualTo(10000);
    }

    @Test
    @DisplayName("멤버십이 존재하는지 여부")
    public void MembershipFindResult() {
        // given
        final Membership membership = Membership.builder()
                .userId("userId")
                .membershipType(MemebershipType.NAVER)
                .point(10000)
                .build();

        // when
        membershipRepository.save(membership);
        final Membership findResult = membershipRepository.findByUserIdAndMembershipType("userId", MemebershipType.NAVER);

        // then
        Assertions.assertThat(findResult).isNotNull();
        Assertions.assertThat(findResult.getId()).isNotNull();
        Assertions.assertThat(findResult.getUserId()).isEqualTo("userId");
        Assertions.assertThat(findResult.getMembershipType()).isEqualTo(MemebershipType.NAVER);
        Assertions.assertThat(findResult.getPoint()).isEqualTo(10000);
    }
}
