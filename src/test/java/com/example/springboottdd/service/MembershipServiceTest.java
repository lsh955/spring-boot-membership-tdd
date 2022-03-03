package com.example.springboottdd.service;

import com.example.springboottdd.domain.Membership;
import com.example.springboottdd.enums.MemebershipType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author 이승환
 * @since 2022-03-02
 */
@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    private final String userId = "userId";
    private final MemebershipType memebershipType = MemebershipType.NAVER;
    private final Integer point = 10000;

    @Test
    @DisplayName("멤버십등록실패_이미존재함")
    public void 멤버십등록실패_이미존재함() {
        // given
        Mockito.doReturn(Membership.builder().build()).when(memebershipType).findByUserIdAndMembershipType(userId, memebershipType);

        // when
        final MembershipException result = Assertions.assertThrows(MembershipErrorResult.class, () -> target.addMembership(userId, memebershipType, point));

        // then
        org.assertj.core.api.Assertions.assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);
    }
}
