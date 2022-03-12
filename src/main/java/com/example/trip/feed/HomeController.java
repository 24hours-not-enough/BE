package com.example.trip.feed;

import com.example.trip.redis.ChatSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class HomeController {
    private final FeedService feedService;
    @GetMapping("/map")
    public void getEveryFeed() {
        feedService.findAll();
    }
}
