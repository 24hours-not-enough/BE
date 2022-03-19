package com.example.trip.service;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.*;
import com.example.trip.dto.*;
import com.example.trip.exceptionhandling.CustomException;
import com.example.trip.repository.*;
import com.example.trip.repository.BookMarkRepository;
import com.example.trip.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.example.trip.exceptionhandling.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedDetailLocImgRepository feedDetailLocImgRepository;
    private final BookMarkRepository bookmarkRepository;
    private final FeedCommentRepository feedCommentRepository;
    private final PlanRepository planRepository;
    private final S3UploaderServiceImpl s3UploaderService;

    // 나의 전체 여행 기록 목록 보기 -> cache 필요
    public List<FeedResponseDto.AllMyTrips> showAllMyFeeds(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = Optional.ofNullable(userRepository.findBySocialaccountId(userDetails.getUsername())).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        User foundUser = user.get();
        List<Feed> feeds = feedRepository.findByUserId(foundUser.getId());
        ArrayList<FeedResponseDto.AllMyTrips> array = new ArrayList<>();
        for (Feed feed : feeds) {
            List<FeedDetailLocImg> imgs = feedDetailLocImgRepository.FindFeedandImgs(feed.getId());
            ArrayList<String> arr = new ArrayList<>();
            for (FeedDetailLocImg img : imgs) {
                arr.add(img.getImgUrl());
            }
            FeedResponseDto.AllMyTrips dto = new FeedResponseDto.AllMyTrips(feed.getTitle(), feed.getTravelStart(), feed.getTravelEnd(), arr);
            array.add(dto);
        }
        return array;
    }

    // 캐시 작업 X
    public List<BookmarkResponseDto> getBookmarkPlaces(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findBySocialaccountId(userDetails.getUsername());
        List<BookMark> bookMarks = bookmarkRepository.FindBookmarkByUserId(user.get().getId());
        ArrayList<BookmarkResponseDto> arrayList = new ArrayList<>();
        for (BookMark bookMark: bookMarks) {
            BookmarkResponseDto dto = new BookmarkResponseDto(
                    bookMark.getFeedDetailLoc().getId(),
                    bookMark.getFeedDetailLoc().getLocation(),
                    bookMark.getFeedDetailLoc().getCity());
            arrayList.add(dto);
        }
        return arrayList;
    }

    // 여행 기록 1개 전체 보기 (조회) -> cache 작업 필요
    public FeedResponseDto.ReadOneTrip readOneTrip(UserDetailsImpl userDetails, Long feedId) {
        Optional<User> user = userRepository.findBySocialaccountId(userDetails.getUsername());
        Feed feed = feedRepository.FindFeedByUserId(user.get().getId(), feedId);
        List<FeedDetail> feedDetails = feed.getFeedDetail();
        ArrayList<FeedDetailResponseDto> FeedDetailList = new ArrayList<>();
        for (FeedDetail feedDetail: feedDetails) {
            List<FeedDetailLoc> feedDetailLocs = feedDetail.getFeedDetailLoc();
            ArrayList<FeedDetailLocResponseDto> feedDetailLocList = new ArrayList<>();
            for(FeedDetailLoc feedDetailLoc: feedDetailLocs) {
                List<FeedDetailLocImg> feedDetailLocImgs = feedDetailLoc.getFeedDetailLocImg();
                ArrayList<String> imgUrls = new ArrayList<>();
                for(FeedDetailLocImg feedDetailLocImg:feedDetailLocImgs) {
                    String feedDetailLocImgUrl = feedDetailLocImg.getImgUrl();
                    imgUrls.add(feedDetailLocImgUrl);
                }
                FeedDetailLocResponseDto feedDetailLocResponseDto = new FeedDetailLocResponseDto(feedDetailLoc.getLocation(), feedDetailLoc.getCity(), feedDetailLoc.getComment(), imgUrls);
                feedDetailLocList.add(feedDetailLocResponseDto);
            }
            FeedDetailResponseDto dto = new FeedDetailResponseDto(feedDetail.getId(), feedDetail.getDay(), feedDetailLocList);
            FeedDetailList.add(dto);
        }

        return new FeedResponseDto.ReadOneTrip(feed.getId(), feed.getTitle(), feed.getTravelStart(), feed.getTravelEnd(), FeedDetailList);
    }

    // feed 1개 읽기(조회) -> cache 작업 필요
    @Cacheable(value = "feed", key = "#feeddetaillocId")
    public FeedDetailLocCommentResponseDto readOneFeed(Long feeddetaillocId) {
        Optional<FeedDetailLoc> byId = feedDetailLocRepository.findById(feeddetaillocId);
        FeedDetailLoc locationData = byId.get();

        List<String> imgUrls = feedDetailLocImgRepository.FindImgsByFeedDetailLocId(feeddetaillocId);
        List<FeedComment> comments = feedCommentRepository.FindFeedCommentByFeedDetailLocId(feeddetaillocId);

        ArrayList<FeedCommentResponseDto> commentsList = new ArrayList<>();
        comments.stream().forEach(comment -> commentsList.add(new FeedCommentResponseDto(comment.getUser().getUsername(), comment.getContent())));

        return new FeedDetailLocCommentResponseDto(locationData.getLocation(), locationData.getCity(), locationData.getComment(), imgUrls, commentsList);

    }

    // 캐시 작업 X
    public List<LikesResponseDto.SortByCity> sortLikesFeed(UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findBySocialaccountId(userDetails.getUsername());
        List<FeedDetailLoc> totalCityList = feedDetailLocRepository.FindAllCityList(user.get().getId());
        ArrayList<LikesResponseDto.SortByCity> likesFeedList = new ArrayList<>();
        for(FeedDetailLoc city:totalCityList) {
            List<FeedDetailLoc> oneCityList = feedDetailLocRepository.FindOneCityList(user.get().getId(), city.getCity());
            ArrayList<String> images = new ArrayList<>();
            if (oneCityList.size() < 3) {
                for(FeedDetailLoc onecity:oneCityList) {
                    images.add(onecity.getFeedDetailLocImg().get(1).getImgUrl());
                }
            } else {
                for(int i=0; i<3; i++) {
                    images.add(oneCityList.get(i).getFeedDetailLocImg().get(1).getImgUrl());
                }
            }
            LikesResponseDto.SortByCity dto = new LikesResponseDto.SortByCity(city.getCity(), oneCityList.size(), images);
            likesFeedList.add(dto);
        }
        return likesFeedList;
    }

    // longitude + latitude 값 제거해서 return 하는 방법?
    public MypageResponseDto.GetPlan getPlan(Long planId, UserDetailsImpl userDetails) {
        Optional<Plan> plan = planRepository.findById(planId);
        Plan foundPlan = plan.get();
        return new MypageResponseDto.GetPlan(foundPlan);
    }

    // 마이페이지 프로필 수정(완료)
    @Transactional
    public UserBasicInfoResponseDto changeProfile(UserDetailsImpl userDetails, String username, MultipartFile file) throws IOException {
        Optional<User> user = userRepository.findBySocialaccountId(userDetails.getUsername());
        Map<String, String> nameUrl = s3UploaderService.upload(file);
        s3UploaderService.deleteFile(user.get().getImage().getFilename());
        user.get().update(username, nameUrl.get(file.getOriginalFilename()), file.getOriginalFilename());
        return new UserBasicInfoResponseDto(username, nameUrl.get(file.getOriginalFilename()));
    }
}
