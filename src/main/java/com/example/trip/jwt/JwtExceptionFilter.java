package com.example.trip.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            sendTokenException(HttpStatus.UNAUTHORIZED, response);
        }
    }

    private void sendTokenException(HttpStatus httpstatus, HttpServletResponse response) throws IOException {
        response.setStatus(httpstatus.value());
        response.setContentType("application/json; charset=UTF-8");

        JSONObject body = new JSONObject();
        body.put("status", 401);
        body.put("error message", "ACCESSTOKEN 유효기간 만료, 재발급 필요");

        PrintWriter out = response.getWriter();
        out.print(body);
    }
}
