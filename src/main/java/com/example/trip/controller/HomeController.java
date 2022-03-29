package com.example.trip.controller;

import com.example.trip.dto.response.AllLocationsListDto;
import com.example.trip.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class HomeController {
    private final FeedService feedService;
    @PostMapping("/map")
    public ResponseEntity<AllLocationsListDto>   getEveryFeed() {
        return new ResponseEntity<>(
                AllLocationsListDto.builder().allLocationsDtoList(feedService.findAll()).build(), HttpStatus.OK);
    }
}
