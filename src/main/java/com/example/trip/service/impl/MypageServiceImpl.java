package com.example.trip.service.impl;

import com.example.trip.advice.exception.UserNotFoundException;
import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.*;
import com.example.trip.dto.response.UserResponseDto;
import com.example.trip.repository.*;
import com.example.trip.repository.BookMarkRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedDetailLocImgRepository feedDetailLocImgRepository;
    private final BookMarkRepository bookmarkRepository;
    private final PlanRepository planRepository;
    private final S3UploaderServiceImpl s3UploaderService;

    // 나의 전체 여행 기록 목록 보기 -> cache 필요
//    @Cacheable(value = "feedlist", key = "#userId")
//    public List<FeedResponseDto.AllMyTrips> showAllMyFeeds(Long userId) {
//        List<Feed> feeds = feedRepository.findByUserId(userId);
//        ArrayList<FeedResponseDto.AllMyTrips> arr = new ArrayList<>();
//        for (Feed feed : feeds) {
//            List<FeedDetailLocImg> imgs = feedDetailLocImgRepository.FindFeedandImgs(feed.getId());
//            List<String> imageList = imgs.stream().map(x -> x.getImgUrl()).collect(Collectors.toList());
//            arr.add(new FeedResponseDto.AllMyTrips(feed.getTitle(), feed.getTravelStart(), feed.getTravelEnd(), imageList.size(), imageList));
//        }
//        return arr;
//    }
//
//    // 캐시 작업 X
//    public List<BookmarkResponseDto> getBookmarkPlaces(Long userId) {
//        List<BookMark> bookMarks = bookmarkRepository.FindBookmarkByUserId(userId);
//        ArrayList<BookmarkResponseDto> arr = new ArrayList<>();
//        for (BookMark bookMark: bookMarks) {
//            BookmarkResponseDto dto = new BookmarkResponseDto(
//                    bookMark.getFeedDetailLoc().getId(),
//                    bookMark.getFeedDetailLoc().getLocation(),
//                    bookMark.getFeedDetailLoc().getCity());
//            arr.add(dto);
//        }
//        return arr;
//    }
//
//    // 여행 기록 1개 전체 보기 (조회) -> cache 작업 필요
//    @Cacheable(value = "feed", key = "#feedId")
//    public FeedResponseDto.ReadOneTrip readOneTrip(Long userId, Long feedId) {
//        FeedValidation(feedId);
//        Feed feed = authFeedValidation(userId, feedId);
//        return new FeedResponseDto.ReadOneTrip(feed);
//    }
//
//    // feed 1개 읽기(조회) -> cache 작업 필요
//    @Cacheable(value = "feeddetailloc", key = "#feeddetaillocId")
//    public FeedDetailLocResponseDto.ReadOneFeed readOneFeed(Long feeddetaillocId) {
//        FeedDetailLocValidation(feeddetaillocId);
//        Optional<FeedDetailLoc> byId = feedDetailLocRepository.findById(feeddetaillocId);
//        FeedDetailLoc locationData = byId.get();
//        return new FeedDetailLocResponseDto.ReadOneFeed(locationData);
//    }
//
    // 캐시 작업 X
//    public List<LikesResponseDto.SortByCity> sortLikesFeed(String socialaccountId) {
//        Optional<User> user = userRepository.findBySocialaccountId(socialaccountId);
//        List<FeedDetailLoc> totalCityList = feedDetailLocRepository.FindAllCityList(user.get().getId());
//        ArrayList<LikesResponseDto.SortByCity> likesFeedList = new ArrayList<>();
//        for(FeedDetailLoc city:totalCityList) {
//            List<FeedDetailLoc> oneCityList = feedDetailLocRepository.FindOneCityList(user.get().getId(), city.getCity());
//            ArrayList<String> images = new ArrayList<>();
//            if (oneCityList.size() < 3) {
//                for(FeedDetailLoc onecity : oneCityList) {
//                    images.add(onecity.getFeedDetailLocImg().get(1).getImgUrl());
//                }
//            } else {
//                for(int i=0; i<3; i++) {
//                    images.add(oneCityList.get(i).getFeedDetailLocImg().get(1).getImgUrl());
//                }
//            }
//            LikesResponseDto.SortByCity dto = new LikesResponseDto.SortByCity(city.getCity(), oneCityList.size(), images);
//            likesFeedList.add(dto);
//        }
//        return likesFeedList;
//    }
//
//    public MypageResponseDto.GetPlan getPlan(Long planId, Long userId) {
//        authPlanValidation(planId);
//        Plan plan = authPlanMemberValidation(planId, userId);
//        return new MypageResponseDto.GetPlan(plan);
//    }

    // 마이페이지 프로필 수정(완료)
    @CacheEvict(value = "userprofile")
    @Transactional
    public UserResponseDto.UserProfile changeProfile(UserDetailsImpl userDetails, String username, MultipartFile file) throws IOException {
        Optional<User> user = Optional.ofNullable(userRepository.findBySocialaccountId(userDetails.getUsername()).orElseThrow(UserNotFoundException::new));
        Map<String, String> nameUrl = s3UploaderService.upload(file);
        s3UploaderService.deleteFile(user.get().getImage().getFilename());
        user.get().update(username, nameUrl.get(file.getOriginalFilename()), file.getOriginalFilename());
        return new UserResponseDto.UserProfile(user.get().getId(), username, nameUrl.get(file.getOriginalFilename()));
    }

//    private Feed authFeedValidation(Long userId, Long feedId) {
//        return feedRepository.FindFeedByUserId(userId, feedId).orElseThrow(AuthFeedNotFoundException::new);
//    }
//
//    // mock data 넣어서 확인 필요
//    private Plan authPlanMemberValidation(Long planId, Long userId) {
//        return planRepository.findByPlanAndUser(planId, userId).orElseThrow(AuthPlanNotFoundException::new);
//    }
//
//    private void authPlanValidation(Long planId) {
//        planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
//    }
//
//    private void FeedDetailLocValidation(Long feeddetaillocId) {
//        feedDetailLocRepository.findById(feeddetaillocId).orElseThrow(FeedDetailLocNotFoundException::new);
//    }
//
//    private void FeedValidation(Long feedId) {
//        feedRepository.findById(feedId).orElseThrow(FeedNotFoundException::new);
//    }
}
