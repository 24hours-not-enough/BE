package com.example.trip.service;

import com.example.trip.advice.exception.*;
import com.example.trip.domain.*;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.response.AllLocationsDto;
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


    public List<AllLocationsDto> findAll() {

        List<FeedLocation> feedLocations = feedLocationRepository.findAll();
        List<AllLocationsDto> allLocationsDtos = new ArrayList<>();
        for(FeedLocation feedLocation : feedLocations) {
            AllLocationsDto allLocationsDto = AllLocationsDto.builder()
                    .placeId(feedLocation.getId())
                    .locationName(feedLocation.getName())
                    .latitude(feedLocation.getLatitude())
                    .longitude(feedLocation.getLongitude())
                    .build();
            allLocationsDto.toFeedPerLocation(feedLocation.getFeedDetailLocs());
            allLocationsDtos.add(allLocationsDto);
        }
        return allLocationsDtos;
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

        //feed Detail 저장
        feedRequestRegisterDto.getFeedDetail().stream()
                .forEach(x -> x.setFeed(newFeed));
        List<FeedDetail> feedDetails = feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());

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
        feedDetailLocs.stream()
                .forEach(x -> feedDetailLocRepository.saveAll(x));



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
    public void modifyFeed(User user, Long feedId, FeedRequestDto.FeedRequestRegisterDto feedRequestModifyDto) {
        // 피드를 올린 사람만 권한이 있어야함
//        List<Feed> myFeed = feedRepository.findByIdAndUserId(feedId, user.getId());
//
//        if (myFeed.isEmpty()) {
//            throw new AuthFeedNotFoundException();
//        }

//Feed 수정
        feedRepository.deleteById(feedId);
        registerFeed(user, feedRequestModifyDto);
//        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new FeedNotFoundException());
//        feed.update(feedRequestModifyDto);
//
//        List<FeedDetail> feedDetails = feedDetailRepository.findByFeedId(feedId);
//        feedDetailRepository.deleteByFeedId(feedId);
//
//        //feed Detail 수정
//        feedDetails.forEach(x -> x.update(
//                feedRequestModifyDto.getFeedDetail().get(feedDetails.indexOf(x))
//        ));
//
//        //feed DetailLoc 수정
//        List<FeedDetailLoc> feedDetailLocs = new ArrayList<>();
//        feedDetails.forEach(x ->
//                feedDetailLocRepository.findByFeedDetailId(x.getId())
//                        .forEach(y -> feedDetailLocs.add(y))
//                );
//
//        feedDetailLocs.forEach(x -> x.update(
//                feedRequestModifyDto.getFeedDetail().stream()
//                        .map(FeedDetail::getFeedDetailLoc)
//                        .flatMap(List<FeedDetailLoc>::stream)
//                        .collect(Collectors.toList())
//                        .get(feedDetailLocs.indexOf(x)
//
//        )));
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
}
