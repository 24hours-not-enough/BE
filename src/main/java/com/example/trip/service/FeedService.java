package com.example.trip.service;

import com.example.trip.advice.exception.*;
import com.example.trip.domain.*;
import com.example.trip.dto.response.FeedDetailLocResponseDto.GetFeedDetailLoc;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedDetailRepository feedDetailRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedDetailLocImgRepository feedDetailLocImgRepository;
    private final FeedLocationRepository feedLocationRepository;


    public List<Feed> findAll() {

        return feedRepository.findAll();
    }

    @Caching(evict = { @CacheEvict(value = "feedlist",
            key = "#user.id"), @CacheEvict(value = "feed",
            key = "#feedId", condition = "#feedId != null"),
            @CacheEvict(value = "feeddetailloc", key = "#feeddetaillocId", condition = "#feeddetaillocId != null") })
    @Transactional
    public List<Long> registerFeed(User user, FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto) {
        // feed 저장
        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .build();

        Feed newFeed = feedRepository.save(feed);
        System.out.println("Feed 저장");
        //feed Detail 저장
        feedRequestRegisterDto.getFeedDetail().stream()
                .forEach(x -> x.setFeed(newFeed));
        List<FeedDetail> feedDetails = feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());
        System.out.println("Feed Detail 저장");

        //feed DetailLoc 저장
        feedRequestRegisterDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.setFeedDetail(x)));

        List<List<FeedDetailLoc>> feedDetailLocs = feedDetails.stream()
                .map(FeedDetail::getFeedDetailLoc)
                .collect(Collectors.toList());

        List<FeedLocation> feedLocations = feedDetailLocs.stream()
                        .flatMap(List<FeedDetailLoc>::stream)
                                .map(FeedDetailLoc::getFeedLocation)
                                        .collect(Collectors.toList());
        feedLocationRepository.saveAll(feedLocations);
        System.out.println("Feed Location 저장");
        feedDetailLocs.stream()
                .forEach(x -> feedDetailLocRepository.saveAll(x));
        System.out.println("Feed Detail Loc 저장");



        //feed DetailLocImg 저장
        feedRequestRegisterDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.getFeedDetailLocImg().forEach(z -> z.setFeedDetailLoc(y))));

        List<List<FeedDetailLocImg>> feedDetailLocImgs = feedDetailLocs.stream()
                .flatMap(Collection::stream)
                .map(FeedDetailLoc::getFeedDetailLocImg)
                .collect(Collectors.toList());

        feedDetailLocImgs.forEach(x -> feedDetailLocImgRepository.saveAll(x));


        List<Long> newFeedDetailLocs = feedDetailLocRepository.findByFeedId(newFeed.getId());
        return newFeedDetailLocs;
    }


    @Caching(evict = { @CacheEvict(value = "feedlist",
            key = "#user.id"), @CacheEvict(value = "feed",
            key = "#feedId", condition = "#feedId != null"),
            @CacheEvict(value = "feeddetailloc", key = "#feeddetaillocId", condition = "#feeddetaillocId != null") })
    @Transactional
    public void modifyFeed(User user, Long feedId, FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto) {
        // 피드를 올린 사람만 권한이 있어야함
        List<Feed> myFeed = feedRepository.findByIdAndUserId(feedId, user.getId());

        if (myFeed.isEmpty()) {
            throw new AuthFeedNotFoundException();
        }


        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new FeedNotFoundException());

        //feed Detail 수정
        feedRequestModifyDto.getFeedDetail().stream()
                .forEach(x -> x.setFeed(feed));

        //feed DetailLoc 수정
        feedRequestModifyDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.setFeedDetail(x)));

        //feed DetailLocImg 수정
        feedRequestModifyDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.getFeedDetailLocImg().forEach(z -> z.setFeedDetailLoc(y))));

        feed.update(feedRequestModifyDto);
    }


    @Caching(evict = { @CacheEvict(value = "feedlist",
            key = "#user.id"), @CacheEvict(value = "feed",
            key = "#feedId", condition = "#feedId != null"),
            @CacheEvict(value = "feeddetailloc", key = "#feeddetaillocId", condition = "#feeddetaillocId != null") })
    public void deleteFeed(User user, Long feedId) {
        // 피드를 올린 사람만 권한이 있어야함
        List<Feed> myFeed = feedRepository.findByIdAndUserId(feedId, user.getId());

        if (myFeed.isEmpty()) {
            throw new AuthFeedNotFoundException();
        }
        feedRepository.deleteById(feedId);
    }

    public List<FeedResponseDto.GetFeed> getFeeds(Long userId) {
        List<Feed> feeds = feedRepository.findByUserId(userId);
        ArrayList<FeedResponseDto.GetFeed> arr = new ArrayList<>();
        for (Feed feed : feeds) {
            FeedResponseDto.GetFeed dto = new FeedResponseDto.GetFeed(feed);
            arr.add(dto);
        }
        return arr;
    }

    public List<Map<String, List<GetFeedDetailLoc>>> getLikeFeeds(Long userId) {
        List<FeedDetailLoc> addressList = feedDetailLocRepository.findAddressCnt(userId);

        ArrayList<Map<String, List<GetFeedDetailLoc>>> arr = new ArrayList<>();

        for(FeedDetailLoc address : addressList) {
            Map<String, List<GetFeedDetailLoc>> map = new HashMap<>();
            List<FeedDetailLoc> locations = feedDetailLocRepository.findLocationsByLikes(userId, address.getFeedLocation().getPlaceAddress());
            List<GetFeedDetailLoc> oneCityLocations = locations.stream().map(GetFeedDetailLoc::new).collect(Collectors.toList());
            map.put(address.getFeedLocation().getPlaceAddress(), oneCityLocations);

            arr.add(map);

        }
        return arr;
    }


}
