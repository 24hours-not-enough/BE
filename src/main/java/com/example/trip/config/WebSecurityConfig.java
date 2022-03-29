package com.example.trip.config;

import com.example.trip.jwt.JwtAuthenticationFilter;
import com.example.trip.jwt.JwtTokenProvider;
import com.example.trip.jwt.exceptionhandler.AccessDeniedHandler;
import com.example.trip.jwt.exceptionhandler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                    .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 사용 예정, 세션 필요 X
                    .and()
                .csrf().disable() // rest api는 csrf 보안 필요 X
                .formLogin().disable() // 스프링 시큐리티 login form 사용 X
                .httpBasic().disable(); // rest api 이므로 기본 설정 사용 X -> 기본 설정은 비인증 시 로그인폼 화면으로 리다이렉트

//                //여기서부터
//                .csrf().disable() // rest api는 csrf 보안 필요 X
//                .headers()
//                    .frameOptions().sameOrigin()
//                .and()
//                    .formLogin()
//
//                .and()
//                        .authorizeRequests()
//                            .antMatchers("/chat/**").permitAll()
//                            .anyRequest().authenticated(); // 위 antMatchers 이외에는 모든 api 인증 필요
//                //여기까지


        http
               .authorizeRequests()
               .antMatchers("/api/kakaologin", "/api/googlelogin", "/", "/api/map").permitAll()
               .anyRequest().authenticated() // 위 antMatchers 이외에는 모든 api 인증 필요
                    .and()
               .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인가 관련
                .accessDeniedHandler(new AccessDeniedHandler()); // 인증 관련
    }
}
