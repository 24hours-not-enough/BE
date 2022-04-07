package com.example.trip.config;

import com.example.trip.jwt.JwtAuthenticationFilter;
import com.example.trip.jwt.JwtExceptionFilter;
import com.example.trip.jwt.JwtTokenProvider;
import com.example.trip.jwt.exceptionhandler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;

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


        http
                .authorizeRequests()
                .antMatchers("/api/kakaologin", "/api/googlelogin", "/", "/api/map").permitAll()
                .antMatchers(HttpMethod.POST, "/api/token").permitAll()
                .antMatchers("/alarmpoint/**","/endpoint/**", "/webjars/sockjs-client/sockjs.min.js", "/webjars/stomp-websocket/stomp.min.js", "/index.html","/app.js").permitAll()
//                .antMatchers("**").permitAll()
                .anyRequest().authenticated() // 위 antMatchers 이외에는 모든 api 인증 필요
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());


    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
