package com.example.trip.feed;


import com.example.trip.config.security.UserDetailsImpl;
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
    public ResponseEntity<FeedResponseDto> registerFeed(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody FeedRequestDto.FeedRequestRegisterDto feedRegisterDto) {
        feedService.registerFeed(user.getUser(), feedRegisterDto);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 등록 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/feed/{feedId}")
    public ResponseEntity<FeedResponseDto> modifyFeed(@PathVariable Long feedId, @RequestBody FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto) {
        feedService.modifyFeed(feedId, feedRequestModifyDto);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 수정 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedId}")
    public ResponseEntity<FeedResponseDto> deleteFeed(@PathVariable Long feedId) {
        feedService.deleteFeed(feedId);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/feed/{feedDetailLocId}/like")
    public ResponseEntity<FeedResponseDto> likeFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.likeFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 좋아요 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unlike")
    public ResponseEntity<FeedResponseDto> unlikeFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.unlikeFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 좋아요 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/feed/{feedDetailLocId}/bookmark")
    public ResponseEntity<FeedResponseDto> bookmarkFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.bookmarkFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 북마크 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unbookmark")
    public ResponseEntity<FeedResponseDto> unbookmarkFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.unbookmarkFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 북마크 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}
