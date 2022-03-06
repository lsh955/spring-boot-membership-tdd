package com.example.springboottdd.controller;

import com.example.springboottdd.common.GlobalExceptionHandler;
import com.example.springboottdd.dto.MembershipRequest;
import com.example.springboottdd.enums.MembershipErrorResult;
import com.example.springboottdd.enums.MembershipType;
import com.example.springboottdd.exception.MembershipException;
import com.example.springboottdd.service.MembershipService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.springboottdd.constants.MembershipConstants.USER_ID_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 이승환
 * @since 2022-03-04
 */
@ExtendWith(MockitoExtension.class)
public class MembershipControllerTest {

    @InjectMocks
    private MembershipController membershipController;

    @Mock
    private MembershipService membershipService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(membershipController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("MockMvc 테스트(MockMvc 가 Null 인지 여부)")
    public void mockMvc가Null이아님() throws Exception {
        assertThat(membershipController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @DisplayName("맴버십등록 실패(사용자 식별값이 헤더에 없음)")
    public void 멤버십등록실패_사용자식별값이헤더에없음() throws Exception {
        // given
        final String url = "/api/v1/membership";

        // when
        final ResultActions resultActions = mockMvc.perform(
                 MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(membershipRequest(10000, MembershipType.NAVER)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("멤버십등록 실패(포인트가 null인 경우)")
    public void 멤버십등록실패_포인트가null() throws Exception {
        // given
        final String url = "/api/v1/membership";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "12345")
                        .content(gson.toJson(membershipRequest(null, MembershipType.NAVER)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("멤버십등록 실패(포인트가 음수인 경우)")
    public void 멤버십등록실패_포인트가음수() throws Exception {
        // given
        final String url = "/api/v1/membership";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "12345")
                        .content(gson.toJson(membershipRequest(-1, MembershipType.NAVER)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("멤버십등록 실패(맴버십 종류가 null인 경우)")
    public void 멤버십등록실패_멤버십종류가Null() throws Exception {
        // given
        final String url = "/api/v1/membership";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "12345")
                        .content(gson.toJson(membershipRequest(10000, null)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("맴버십등록 실패(MemberService 에서의 에러인 경우)")
    public void 멤버십등록실패_MemberService에서에러Throw() throws Exception {
        // given
        final String url = "/api/v1/membership";
        doThrow(new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER))
                .when(membershipService)
                .addMembership("12345", MembershipType.NAVER, 10000);

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "12345")
                        .content(gson.toJson(membershipRequest(10000, MembershipType.NAVER)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    private MembershipRequest membershipRequest(final Integer point, final MembershipType membershipType) {
        return MembershipRequest.builder()
                .point(point)
                .membershipType(membershipType)
                .build();
    }
}
