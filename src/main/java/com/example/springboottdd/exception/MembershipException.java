package com.example.springboottdd.exception;

import com.example.springboottdd.enums.MembershipErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이승환
 * @since 2022-03-03
 */
@Getter
@RequiredArgsConstructor
public class MembershipException extends RuntimeException {

    private final MembershipErrorResult errorResult;
}
