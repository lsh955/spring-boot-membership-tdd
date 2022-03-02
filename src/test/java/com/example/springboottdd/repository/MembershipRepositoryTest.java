package com.example.springboottdd.repository;

import com.example.springboottdd.domain.Membership;
import com.example.springboottdd.enums.MemebershipType;
import org.assertj.core.api.Assertions;
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
    public void 멤버십등록() {
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
}
