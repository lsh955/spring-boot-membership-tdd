package com.example.springboottdd.exception;

import com.example.springboottdd.enums.MembershipErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이승환
 * @since 2022-03-03
 * <p>
 * MembershipException이 throw 되었을 때
 * RestControllerAdvice를 통해 MembershipErrorResult의 HttpStatus와 message를 반환하기 위함
 */
@Getter
@RequiredArgsConstructor
public class MembershipException extends RuntimeException {

    private final MembershipErrorResult errorResult;
}
