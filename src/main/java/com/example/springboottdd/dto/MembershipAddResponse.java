package com.example.springboottdd.dto;

import com.example.springboottdd.enums.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이승환
 * @since 2022-03-03
 */
@Getter
@Builder
@RequiredArgsConstructor
public class MembershipAddResponse {

    private final Long id;
    private final MembershipType membershipType;
}
