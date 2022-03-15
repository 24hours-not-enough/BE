package com.example.trip.feed;


import com.example.trip.config.security.UserDetailsImpl;
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
    public ResponseEntity<FeedResponseDto.FeedResponseRegisterDto> registerFeed(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody FeedRequestDto.FeedRequestRegisterDto feedRegisterDto) {
        System.out.println("USER : " +  user.getUser());
        feedService.registerFeed(user.getUser(), feedRegisterDto);
        return new ResponseEntity<>(new FeedResponseDto.FeedResponseRegisterDto("success","피드 등록에 성공하였습니다."), HttpStatus.OK);
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
