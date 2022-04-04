package com.example.trip.controller;

import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.response.AllLocationsListDto;
import com.example.trip.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class HomeController {
    private final FeedService feedService;
    @PostMapping("/map")
    public ResponseEntity<AllLocationsListDto>   getEveryFeed(
            @RequestBody FeedRequestDto.FeedRequestMainGetDto feedRequestMainGetDto)
    {
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(
                AllLocationsListDto.builder()
                        //지역별 피드 정보 조회
                        .allLocationsDtoList(feedService.findEachLocations(feedRequestMainGetDto))
                        .build(), HttpStatus.OK);
    }
}

