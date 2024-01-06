package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final AccessTokenAuthentificationFilter accessTokenAuthentificationFilter;

    public WebSecurityConfig(AccessTokenAuthentificationFilter accessTokenAuthentificationFilter) {
        this.accessTokenAuthentificationFilter = accessTokenAuthentificationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //남의 사이트 보여줄떄 form 가로채기 csrf 토큰 방지 api호출이 아닌경우
        http.csrf(AbstractHttpConfigurer::disable);
        //인증과정을 HTTP요청전 필터를 추가할거야
        http.addFilterBefore(accessTokenAuthentificationFilter, BasicAuthenticationFilter.class);
        //모든 HTTP요청에 인증이 필요할거야
        http.authorizeHttpRequests((auth) ->
                auth.requestMatchers(HttpMethod.POST, "/session").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
