package com.example.springboottdd.dto;

import com.example.springboottdd.enums.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.example.springboottdd.validation.ValidationGroups.MembershipAccumulateMarker;
import static com.example.springboottdd.validation.ValidationGroups.MembershipAddMarker;

/**
 * @author 이승환
 * @since 2022-03-05
 */
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MembershipRequest {

    @NotNull(groups = {MembershipAddMarker.class, MembershipAccumulateMarker.class})
    @Min(value = 0, groups = {MembershipAddMarker.class, MembershipAccumulateMarker.class})
    private final Integer point;

    @NotNull(groups = {MembershipAddMarker.class})
    private final MembershipType membershipType;
}
