package com.example.springboottdd.service;

import org.springframework.stereotype.Service;

/**
 * @author 이승환
 * @since 2022-03-17
 */
@Service
public class RatePointService implements PointService {

    private static final int POINT_RATE = 1;

    @Override
    public int calculateAmount(final int price) {

        return price * POINT_RATE / 100;
    }
}
