package com.example.springboottdd.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이승환
 * @since 2022-03-02
 */
@Getter
@RequiredArgsConstructor
public enum MembershipType {

    NAVER("네이버"),
    LINE("라인"),
    KAKAO("카카오");

    private final String companyName;
}
