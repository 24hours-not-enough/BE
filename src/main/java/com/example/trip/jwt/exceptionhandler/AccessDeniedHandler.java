package com.example.trip.jwt.exceptionhandler;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException acsEx) throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        JSONObject json = new JSONObject();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();

        String message = "페이지 접근 권한이 없습니다.(No Authorization)";
        json.put("result", "fail");
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json);
    }

}
