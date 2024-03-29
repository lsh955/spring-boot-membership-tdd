package com.example.springboottdd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author 이승환
 * @since 2022-03-17
 */
@ExtendWith(MockitoExtension.class)
public class RatePointServiceTest {

    @InjectMocks
    private RatePointService ratePointService;

    @Test
    public void _10000원의_적립은_100원() {
        // given
        final int price = 10000;

        // when
        final int result = ratePointService.calculateAmount(price);

        // then
        assertThat(result).isEqualTo(100);
    }

    @Test
    public void _10000원의_적립은_200원() {
        // given
        final int price = 20000;

        // when
        final int result = ratePointService.calculateAmount(price);

        // then
        assertThat(result).isEqualTo(200);
    }

    @Test
    public void _10000원의_적립은_300원() {
        // given
        final int price = 30000;

        // when
        final int result = ratePointService.calculateAmount(price);

        // then
        assertThat(result).isEqualTo(300);
    }
}
