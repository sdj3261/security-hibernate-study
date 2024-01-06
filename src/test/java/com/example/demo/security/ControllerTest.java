package com.example.demo.security;

import com.example.demo.DemoApplication;
import com.example.demo.infra.UserDetailsDao;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = {
        DemoApplication.class,
        WebSecurityConfig.class
})
public abstract class ControllerTest {
    //뭐좀넣겠습니다.. before each spybean
    @SpyBean
    private AccessTokenService accessTokenService;
    @MockBean
    protected UserDetailsDao userDetailsDao; // ← 여기로 가져온다.

    @BeforeEach
    void setUpUserDetailsDaoForAuthentication() { // ← 이 이름을 똑같이 쓰면 override된다는 점에 주의!
        UserDetails userDetails = User.withUsername("UserID")
                .password("TOKEN")
                .authorities("ROLE_USER")
                .build();

        given(userDetailsDao.findByAccessToken("TOKEN"))
                .willReturn(Optional.of(userDetails));
    }
}
