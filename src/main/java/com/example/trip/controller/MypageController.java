package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.*;
import com.example.trip.response.*;
import com.example.trip.service.MypageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final MypageServiceImpl mypageService;

    // 전체 나의 기록 조회
    @GetMapping("/api/feed")
    public ResponseEntity<MyTotalFeeds> showAllMyFeeds(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FeedResponseDto.AllMyTrips> feeds = mypageService.showAllMyFeeds(userDetails.getUser().getId());
        return new ResponseEntity<>(new MyTotalFeeds("success", "나의 전체 피드를 불러왔습니다.", feeds), HttpStatus.OK);
    }

    // 내 북마크 조회
    @GetMapping("/api/bookmark")
    public ResponseEntity<BookmarkLocation> getBookmarkPlaces(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BookmarkResponseDto> places = mypageService.getBookmarkPlaces(userDetails.getUser().getId());
        return new ResponseEntity<>(new BookmarkLocation("success", "내 북마크 리스트입니다.", places), HttpStatus.OK);
    }

    // 나의 여행 기록 1개 전체 보기
    @GetMapping("/api/mytrip/{feedId}")
    public ResponseEntity<ReadOneTrip> readOneTrip(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long feedId) {
        FeedResponseDto.ReadOneTrip dto = mypageService.readOneTrip(userDetails.getUser().getId(), feedId);
        return new ResponseEntity<>(new ReadOneTrip("success", "여행기록입니다.", dto), HttpStatus.OK);
    }

    // 올라간 게시글 보기
    @GetMapping("/api/feed/{feeddetaillocId}")
    public ResponseEntity<ReadOneFeed> readOneFeed(@PathVariable Long feeddetaillocId) {
        FeedDetailLocResponseDto.ReadOneFeed feedData = mypageService.readOneFeed(feeddetaillocId);
        return new ResponseEntity<>(new ReadOneFeed("success", "장소에 대한 게시글 피드입니다.", feedData), HttpStatus.OK);

    }

    // 좋아요 한 게시물 분류 보기
    @GetMapping("/api/likes")
    public ResponseEntity<SortLikesFeed> sortLikesFeed(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<LikesResponseDto.SortByCity> likesListByCity = mypageService.sortLikesFeed(userDetails);
        return new ResponseEntity<>(new SortLikesFeed("success", "좋아요한 게시물 분류입니다.", likesListByCity), HttpStatus.OK);
    }

//     기록 작성 시 계획 불러오기
    @GetMapping("/api/feed/plan/{planId}")
    public ResponseEntity<FindOnePlan> getPlan(@PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MypageResponseDto.GetPlan plan = mypageService.getPlan(planId, userDetails.getUser().getId());
        return new ResponseEntity<>(new FindOnePlan("success", "계획을 기록 작성 페이지에 불러왔습니다.", plan), HttpStatus.OK);
    }

    // 마이페이지 수정
    @PutMapping("/api/mypage")
    public ResponseEntity<RegisterUserInfoSuccess> changeProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @RequestPart String username,
                                                 @RequestPart MultipartFile file) throws IOException {
        UserBasicInfoResponseDto info = mypageService.changeProfile(userDetails, username, file);
        return new ResponseEntity<>(new RegisterUserInfoSuccess("success", "마이페이지 수정 완료입니다.", info), HttpStatus.OK);
    }


}
