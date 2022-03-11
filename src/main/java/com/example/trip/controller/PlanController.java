package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.domain.Role;
import com.example.trip.domain.User;
import com.example.trip.dto.request.PlanRequest;
import com.example.trip.repository.UserRepository;
import com.example.trip.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlanController {

    private final PlanService planService;

    private final UserRepository userRepository;

    @PostMapping("/plan")
    public ResponseEntity<Success> planAdd(@RequestBody PlanRequest.RegistDto registDto){
        planService.addPlan(registDto);
        return new ResponseEntity<>(new Success(true,"계획 등록 성공!"), HttpStatus.OK);
    }

    @PostConstruct
    public void initialize(){
        User user1 = User.builder()
                .email("bbbb@google.com")
                .kakaoId(1L)
                .memberstatus(true)
                .role(Role.USER)
                .username("제이")
                .build();

        User user2 = User.builder()
                .email("cccc@google.com")
                .kakaoId(2L)
                .memberstatus(true)
                .role(Role.USER)
                .username("깃허브")
                .build();

        User user3 = User.builder()
                .email("dddd@google.com")
                .kakaoId(3L)
                .memberstatus(true)
                .role(Role.USER)
                .username("홍길동")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}
