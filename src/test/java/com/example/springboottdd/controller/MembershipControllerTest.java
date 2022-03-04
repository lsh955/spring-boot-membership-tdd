package com.example.springboottdd.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author 이승환
 * @since 2022-03-04
 */
@ExtendWith(MockitoExtension.class)
public class MembershipControllerTest {

    @InjectMocks
    private MembershipController membershipController;

    private MockMvc mockMvc;
    private Gson gson;

    @Test
    public void mockMvc가Null이아님() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(membershipController)
                .build();

        assertThat(membershipController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }
}
