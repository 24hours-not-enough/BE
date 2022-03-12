package com.example.trip.feed;


import com.example.trip.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/feed")
    public ResponseEntity<FeedResponseDto> registerFeed(@AuthenticationPrincipal User user, FeedRequestDto.FeedRequestRegisterDto feedRegisterDto) {
        feedService.registerFeed(user, feedRegisterDto);
        return new ResponseEntity<>(new FeedResponseDto(), HttpStatus.OK);
    }

//    @PutMapping("/feed")
//    public FeedResponseDto.FeedResponseModifyDto modifyFeed(FeedRequestDto.FeedRequestModifyDto feedRegisterDto) {
//        feedService.modifyFeed();
//    }

//    @DeleteMapping("/feed/{feedId}")
//    public FeedResponseDto.FeedResponseDeleteDto deleteFeed(@PathVariable Long feedId) {
//        feedService.deleteFeed(feedId);
//    }
}
