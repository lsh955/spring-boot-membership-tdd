package com.example.springboottdd.dto;

import com.example.springboottdd.enums.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author 이승환
 * @since 2022-03-05
 */
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MembershipRequest {

    private final Integer point;
    private final MembershipType membershipType;
}
